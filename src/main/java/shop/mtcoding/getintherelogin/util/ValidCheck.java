package shop.mtcoding.getintherelogin.util;

import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

import shop.mtcoding.getintherelogin.handler.exception.CustomApiException;
import shop.mtcoding.getintherelogin.handler.exception.CustomException;

public class ValidCheck {
    
    public static void nullCheck(Object obj, String msg){
        if(ObjectUtils.isEmpty(obj)){
            throw new CustomException(msg);
        }
    }
    public static void nullCheck(Object obj, String msg, HttpStatus status){
        if(ObjectUtils.isEmpty(obj)){
            throw new CustomException(msg, status);
        }
    }
    public static void nullApiCheck(Object obj, String msg){
        if(ObjectUtils.isEmpty(obj)){
            throw new CustomApiException(msg);
        }
    }
    public static void nullApiCheck(Object obj, String msg, HttpStatus status){
        if(ObjectUtils.isEmpty(obj)){
            throw new CustomApiException(msg, status);
        }
    }
    
}
