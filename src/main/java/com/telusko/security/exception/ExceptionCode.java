package com.telusko.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    USER_NOT_FOUND("U-001", "USER doesnt exist" , HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS("U-002" , "User has been added before" , HttpStatus.BAD_REQUEST),

    INVALID_ROLE("R-001" , "Invalid role requested" , HttpStatus.BAD_REQUEST),

    INVALID_REFRESHTOKEN("RT-001" , "Refresh Token Is Not Found"  , HttpStatus.UNAUTHORIZED),
    OLD_REFRESHTOKEN("RT-002", "OLD REFRESH TOKEN HAS BEEN USED" ,HttpStatus.UNAUTHORIZED);

    String errorCode;
    String message;
    HttpStatus httpStatus;


}
