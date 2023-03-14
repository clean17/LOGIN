package shop.mtcoding.getintherelogin.RepositoryTest;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import shop.mtcoding.getintherelogin.dto.user.UserReq.UserJoinReqDto;
import shop.mtcoding.getintherelogin.model.UserRepository;

@MybatisTest
// @RequiredArgsConstructor
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;

    @Test
    public void insert_test() throws Exception {
        // given
        UserJoinReqDto user = UserJoinReqDto.builder()
                                .email("kakao_piw1994@daum.net")
                                .password("1234UUID")
                                .build();
                      
        // when
        userRepository.insert(user);
    
    
        // then
    
    }
}