package com.telusko.Security.exception;

import lombok.Getter;

@Getter
public class UserException extends CustomException{

    public UserException(ExceptionCode code){
        super(code);

    }
}
