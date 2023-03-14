package shop.mtcoding.getintherelogin.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class User {
    private Integer id;
    private String username; // provider 를 추가한
    private String password; // UUID
    private String email;
    private String provider; // me, kakao, naver
}
