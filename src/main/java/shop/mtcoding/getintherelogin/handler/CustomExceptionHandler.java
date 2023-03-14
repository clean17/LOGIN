package shop.mtcoding.getintherelogin.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shop.mtcoding.getintherelogin.dto.ResponseDto;
import shop.mtcoding.getintherelogin.handler.exception.CustomApiException;
import shop.mtcoding.getintherelogin.handler.exception.CustomException;
import shop.mtcoding.getintherelogin.util.Script;


@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customException(CustomException e) {
        return new ResponseEntity<>(Script.back(e.getMessage()), e.getStatus());
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> customApiException(CustomApiException e) {
        return new ResponseEntity<>(new ResponseDto<>(-1, e.getMessage(), null), e.getStatus());
    }
}
