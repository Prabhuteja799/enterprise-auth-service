package com.telusko.Security.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
     ExceptionCode exceptionCode;

     public CustomException(ExceptionCode ex){
         super(ex.getMessage());
         this.exceptionCode=ex;
     }

}
