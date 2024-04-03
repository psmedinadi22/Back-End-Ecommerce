package com.ecommerce.prototype.application.usecase.exception;

public class UnauthorizedCartAccessException extends RuntimeException {
    public UnauthorizedCartAccessException(String message){
        super(message);
    }

}