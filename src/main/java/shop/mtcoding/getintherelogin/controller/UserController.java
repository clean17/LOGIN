package shop.mtcoding.getintherelogin.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.getintherelogin.dto.TokenProperties;
import shop.mtcoding.getintherelogin.dto.UserProperties;
import shop.mtcoding.getintherelogin.dto.user.UserReq.UserJoinReqDto;
import shop.mtcoding.getintherelogin.model.User;
import shop.mtcoding.getintherelogin.model.UserRepository;
import shop.mtcoding.getintherelogin.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private  UserRepository userRepository;
    private final ObjectMapper om;
    private final UserService userService;

    @Autowired
    private  HttpSession session;
    
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/callback")
    @ResponseBody
    public String callBack(String code){
        // 가장 쉬운 보안은 code 를 카카오에 던져서 너가 준게 맞는지 물어보면 됨

        // 1. code 값 존재 유무
        if(ObjectUtils.isEmpty(code)){
            // return "redirect:/loginForm";
            return "bad_request";
        }
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

        HttpEntity<?> httpEntity = new HttpEntity<>(xForm,headers);

        ResponseEntity<String> responseEntity = rt.exchange(kakaoUrl, HttpMethod.POST, httpEntity, String.class);
        // 응답은 토큰을 String으로 처리

        // 3. access token 으로 서버가 리소스( 카카오가 가진 클라이언트의 데이터 ) 에 접근 가능해짐
        // 서버가 카카오에 접근할 권한을 위임받게됨 -> open auth~ -> OAuth
        // System.out.println("테스트 : "+ accessToken);
        String responseBody = responseEntity.getBody();

        // 4. 토큰 파싱
        TokenProperties tp ;
        try {
            tp = om.readValue(responseBody, TokenProperties.class);

            // 5. email 정보 받기
        String kakaoReqUrl2 = "https://kapi.kakao.com/v2/user/me";
        //  -H "Authorization: Bearer ${ACCESS_TOKEN}" \
        //  --data-urlencode 'property_keys=["kakao_account.email"]'
        RestTemplate rt2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders(); // 스프링헤더
        headers2.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers2.setBearerAuth(tp.getAccessToken());
        MultiValueMap<String, String> xForm2 = new LinkedMultiValueMap<>();
        // xForm2.add("property_keys", "kakao_account.email");

        HttpEntity<?> httpEntity2 = new HttpEntity<>(xForm2,headers2);

        // ResponseEntity<String> responseEntity2 = rt2.exchange(kakaoReqUrl2, HttpMethod.POST, httpEntity2, String.class);
        ResponseEntity<String> responseEntity2 = rt2.exchange(kakaoReqUrl2, HttpMethod.GET, httpEntity2, String.class);

        String responseBody2 = responseEntity2.getBody();
        // System.out.println("테스트 : "+responseBody2 );

        UserProperties tp2 ;
        try {
            tp2 = om.readValue(responseBody2, UserProperties.class);
            String resultEmail = "kakao_" + tp2.getKakaoAccount().getEmail();
            // 6. 해당 email로 회원가입되어 있는 정보가 있는지 조회
            // System.out.println("테스트 : "+ resultEmail);
            // User user = User.builder().build();
            try {
                User user = userRepository.findByEmail(resultEmail);
                // 존재하면 계정 찾아서 자동 로그인
                
            } catch (Exception e) {
                // System.out.println("테스트 : "+ user.toString());
                    System.out.println("테스트 : 널");
                    UserJoinReqDto uDto = UserJoinReqDto.builder()
                    .email(resultEmail)
                    .password("1234UUID")
                    .build();
                    User principal = userService.회원가입(uDto);
                    System.out.println("테스트 : 회원가입");
                    session.setAttribute("principal", principal);
            }
            

            // System.out.println("테스트 : "+ resultEmail);

        } catch (Exception e) {
        }
        } catch (Exception e) {
        }

        // 7. 존재하면 그 정보로 세션만들어줌 ( 자동 로그인 )
       
        // 8. 없다면 회원가입 시키고, 그 정보로 세션만듬 ( 자동 로그인 )
        // User user2  = (User) session.getAttribute("principal");
        // return "테스트" + user2.getEmail();
        return "테스트";    
    }

}
