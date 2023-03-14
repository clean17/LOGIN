package shop.mtcoding.getintherelogin.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserProperties {
    public long id;
    public Date connectedAt;
    public KakaoAccount kakaoAccount;

    @Getter
    @Setter
    @ToString
    public static class KakaoAccount{
        public boolean hasEmail;
        public boolean emailNeedsAgreement;
        public boolean isEmailValid;
        public boolean isEmailVerified;
        public String email;
    }
}