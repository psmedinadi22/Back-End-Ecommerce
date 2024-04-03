package com.ecommerce.prototype.application.usecase.exception;

public class InsufficientProductQuantityException extends RuntimeException {
    public InsufficientProductQuantityException(String message){

        super(message);
    }
}
