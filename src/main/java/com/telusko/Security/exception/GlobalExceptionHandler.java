package com.telusko.Security.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorMessage> handleException(CustomException ex){
        ExceptionCode exceptionCode =  ex.getExceptionCode();
        ErrorMessage errorMessage = ErrorMessage.builder().errorCode(exceptionCode.getErrorCode())
                .message(exceptionCode.getMessage()).dateTime(LocalDateTime.now()).build();
        return new ResponseEntity<>(errorMessage,exceptionCode.getHttpStatus());
    }
}
