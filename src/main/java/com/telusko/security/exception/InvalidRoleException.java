package com.telusko.security.exception;

public class InvalidRoleException extends CustomException {
    public InvalidRoleException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
