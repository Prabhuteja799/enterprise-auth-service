package com.telusko.Security.exception;

public class InvalidRoleException extends CustomException {
    public InvalidRoleException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
