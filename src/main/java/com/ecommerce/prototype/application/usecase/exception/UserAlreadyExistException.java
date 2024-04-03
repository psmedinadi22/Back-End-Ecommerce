package com.ecommerce.prototype.application.usecase.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message){
        super(message);
    }

}
