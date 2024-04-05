package com.ecommerce.prototype.application.usecase.exception;

public class UserDisabledException extends RuntimeException {
    public UserDisabledException(String message){
        super(message);
    }
}
