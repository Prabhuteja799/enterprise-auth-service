package com.telusko.security.exception;

import lombok.Getter;

@Getter
public class UserException extends CustomException{

    public UserException(ExceptionCode code){
        super(code);

    }
}
