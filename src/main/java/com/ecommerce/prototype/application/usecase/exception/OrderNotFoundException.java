package com.ecommerce.prototype.application.usecase.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message){

        super(message);
    }
}

