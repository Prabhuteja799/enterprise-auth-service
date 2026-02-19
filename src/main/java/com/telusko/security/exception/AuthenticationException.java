package com.telusko.security.exception;

public class AuthenticationException extends CustomException{

    public AuthenticationException(ExceptionCode ex) {
        super(ex);
    }
}
