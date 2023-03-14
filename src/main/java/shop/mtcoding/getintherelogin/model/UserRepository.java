package shop.mtcoding.getintherelogin.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import shop.mtcoding.getintherelogin.dto.user.UserReq.UserJoinReqDto;

@Mapper
public interface UserRepository {
    public User findByEmail(
        @Param("email") String email);

    public int insert(
        @Param("uDto") UserJoinReqDto uDto
    );
}
