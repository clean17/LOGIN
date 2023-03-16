package shop.mtcoding.getintherelogin.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import shop.mtcoding.getintherelogin.model.User;

public class UserStore {
    public static List<User> userList = new ArrayList<>();

    static {
        userList.add(
            new User(
                1,
                "kakao_2705550308",
                UUID.randomUUID().toString(),
                "ssarmango@gmail.com",
                "kakao")
            );
    }
    public static User findByUsername(String username){
        for (User user: userList) {
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public static void save(User user) {
        userList.add(user);
    }

}

