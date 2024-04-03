package com.ecommerce.prototype.application.usecase.exception;

public class UserNoExistException extends RuntimeException {
    public UserNoExistException(String message){
        super(message);
    }
}
