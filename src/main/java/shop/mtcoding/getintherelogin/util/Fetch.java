package shop.mtcoding.getintherelogin.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.getintherelogin.dto.TokenProperties;
import shop.mtcoding.getintherelogin.handler.exception.CustomException;

public class Fetch<T> {
    public static <T> T parsing(String responseBody, Class<T> clazz, ObjectMapper om){
        // 4. 토큰 파싱
        T t ;
        try {
            t = om.readValue(responseBody, clazz);
        } catch (Exception e) {
            throw new CustomException("파싱 실패");
        }
        return t;
    }

    public static String accessToken(String code){
        // 2. code 값 카카오에게 전달 -> access token 받기
        String kakaoUrl = "https://kauth.kakao.com/oauth/token";
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders(); // 스프링헤더
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 카카오가 요구하는 2차 검증 키들
        MultiValueMap<String, String> xForm = new LinkedMultiValueMap<>();
        xForm.add("grant_type", "authorization_code");
        xForm.add("client_id", "a938dc1f0dcb3a94e621aa3e3394aa5c");
        xForm.add("redirect_uri", "http://localhost:8080/callback");
        xForm.add("code", code);

        HttpEntity<?> httpEntity = new HttpEntity<>(xForm, headers);

        ResponseEntity<String> responseEntity = rt.exchange(kakaoUrl, HttpMethod.POST, httpEntity, String.class);

        // 3. access token 으로 서버가 리소스( 카카오가 가진 클라이언트의 데이터 ) 에 접근 가능해짐
        // 서버가 카카오에 접근할 권한을 위임받게됨 -> open auth~ -> OAuth
        String responseBody = responseEntity.getBody();
        return responseBody;
    }

    public static String requestData(TokenProperties tp){
        // 5. email 정보 받기
        String kakaoReqUrl2 = "https://kapi.kakao.com/v2/user/me";
            // -H "Authorization: Bearer ${ACCESS_TOKEN}" \
            // --data-urlencode 'property_keys=["kakao_account.email"]'
            RestTemplate rt2 = new RestTemplate();
            HttpHeaders headers2 = new HttpHeaders(); // 스프링헤더
            headers2.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers2.setBearerAuth(tp.getAccessToken());
            MultiValueMap<String, String> xForm2 = new LinkedMultiValueMap<>();
            // xForm2.add("property_keys", "kakao_account.email");

            HttpEntity<?> httpEntity2 = new HttpEntity<>(xForm2, headers2);

            // ResponseEntity<String> responseEntity2 = rt2.exchange(kakaoReqUrl2,
            // HttpMethod.POST, httpEntity2, String.class);
            ResponseEntity<String> responseEntity2 = rt2.exchange(kakaoReqUrl2, HttpMethod.GET, httpEntity2,
                    String.class);

            String responseBody2 = responseEntity2.getBody();
            return responseBody2;
    }

    public static ResponseEntity<String> kakao(String url, HttpMethod method, MultiValueMap<String, String> body){
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<?> httpEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> responseEntity = rt.exchange(url, method, httpEntity, String.class);
        return responseEntity;
    }

    public static ResponseEntity<String> kakao(String url, HttpMethod method, String accessToken){
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Bearer "+accessToken);

        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = rt.exchange(url, method, httpEntity, String.class);
        return responseEntity;
    }
}
