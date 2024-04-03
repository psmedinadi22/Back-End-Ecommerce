package com.ecommerce.prototype.application.usecase.exception;

public class PasswordLengthException extends RuntimeException {
    public PasswordLengthException(String message){

        super(message);
    }
}
