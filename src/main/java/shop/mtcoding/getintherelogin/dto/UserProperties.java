package shop.mtcoding.getintherelogin.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class UserProperties {
    private Long id;
    private Timestamp connectedAt;
    private KakaoAccount kakaoAccount;

    @Getter @Setter @ToString
    public static class KakaoAccount{
        private boolean hasEmail;
        private boolean emailNeedsAgreement;
        private boolean isEmailValid;
        private boolean isEmailVerified;
        private String email;
    }

    // public Long id;
    // @JsonProperty("connected_at") // 이렇게 하면 전략 / yml 다 필요 없이 받아줌
    // public Timestamp connectedAt;
    // public KakaoAccount kakaoAccount;

    // @Getter
    // @Setter
    // @ToString
    // public static class KakaoAccount{
    //     public boolean hasEmail;
    //     public boolean emailNeedsAgreement;
    //     public boolean isEmailValid;
    //     public boolean isEmailVerified;
    //     public String email;
    // }
}