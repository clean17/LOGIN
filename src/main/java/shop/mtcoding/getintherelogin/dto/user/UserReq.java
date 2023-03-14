package shop.mtcoding.getintherelogin.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class UserReq {

    @Getter
    @Builder
    // @RequiredArgsConstructor
    @Setter
    @ToString
    public static class UserJoinReqDto{
        private String email;
        private String password;
        // private Timestamp createdAt;
    }
}
