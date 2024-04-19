package com.ecommerce.prototype.application.usecase.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message){

        super(message);
    }
}
