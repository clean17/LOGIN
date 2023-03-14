package shop.mtcoding.getintherelogin.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
// @Builder
public class User {
    private Integer id;
    private String username; // provider 를 추가한
    private String password; // UUID
    private String email;
    private String provider; // me, kakao, naver
    private Timestamp createdAt;
}
