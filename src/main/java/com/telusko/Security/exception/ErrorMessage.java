package com.telusko.Security.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ErrorMessage {

    String errorCode;
    String message;
    LocalDateTime dateTime;
}
