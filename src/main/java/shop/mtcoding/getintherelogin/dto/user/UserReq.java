package shop.mtcoding.getintherelogin.dto.user;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class UserReq {

    @Getter
    @Builder
    // @RequiredArgsConstructor
    @Setter
    public static class UserJoinReqDto{
        private String email;
        private String password;
        // private Timestamp createdAt;
    }
}
