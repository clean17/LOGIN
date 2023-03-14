package shop.mtcoding.getintherelogin.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.getintherelogin.dto.user.UserReq.UserJoinReqDto;
import shop.mtcoding.getintherelogin.model.User;
import shop.mtcoding.getintherelogin.model.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;

    @Transactional
    public User 회원가입 (UserJoinReqDto uDto){
        try {
            userRepository.insert(uDto);
        } catch (Exception e) {
            System.out.println("테스트 : 서비스 터짐");            // TODO: handle exception
        }
        return userRepository.findByEmail(uDto.getEmail());
    }
}
