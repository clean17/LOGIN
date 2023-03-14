package shop.mtcoding.getintherelogin.controller;


import javax.servlet.http.HttpSession;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import shop.mtcoding.getintherelogin.handler.exception.CustomException;
import shop.mtcoding.getintherelogin.model.User;
import shop.mtcoding.getintherelogin.model.UserRepository;
import shop.mtcoding.getintherelogin.service.UserService;
import shop.mtcoding.getintherelogin.util.Fetch;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final ObjectMapper om;
    private final UserService userService;
    private final HttpSession session;
    
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    // 카카오 일경우 .. 메소드를 분리 시키는게 편할듯
    @GetMapping("/callback")
    @ResponseBody
    public String callBack(String code){
        // 1. code 값 존재 유무
        if(ObjectUtils.isEmpty(code)){
            throw new CustomException("잘못된 요청입니다.");
        }
        String responseBody = Fetch.accessToken(code);
        TokenProperties tp = Fetch.parsing(responseBody, TokenProperties.class, om);
        String responseBody2 = Fetch.requestData(tp);
        UserProperties tp2 = Fetch.parsing(responseBody2, UserProperties.class, om);
        String resultEmail = "kakao_" + tp2.getKakaoAccount().getEmail();   
        createSession(resultEmail, userRepository, session, userService); 
        User user2  = (User) session.getAttribute("principal");
        return "테스트" + user2.getEmail(); 
    }

    public static void createSession(String resultEmail, UserRepository userRepository, HttpSession session, UserService userService){
        // 6. 해당 email로 회원가입되어 있는 정보가 있는지 조회
        User user = userRepository.findByEmail(resultEmail);
        if (user != null) {
        // 7. 존재하면 그 정보로 세션만들어줌 ( 자동 로그인 )
            session.setAttribute("principal", user);
        } else {
            UserJoinReqDto uDto = UserJoinReqDto.builder()
                    .email(resultEmail)
                    .password("1234UUID")
                    .build();
        // 8. 없다면 회원가입 시키고, 그 정보로 세션만듬 ( 자동 로그인 )
            User principal = userService.회원가입(uDto);
            session.setAttribute("principal", principal);
        } 
    }
}
