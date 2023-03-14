package shop.mtcoding.getintherelogin.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// @RequiredArgsConstructor
@Setter
@Getter
@ToString
public class TokenProperties { // 언더스코어 카멜로 변환하는 옵션도 추가해야함
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private Integer expiresIn;
    private String scope;
    private Integer refreshTokenExpiresIn;
}
