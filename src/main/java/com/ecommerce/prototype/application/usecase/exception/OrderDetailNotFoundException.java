package com.ecommerce.prototype.application.usecase.exception;

public class OrderDetailNotFoundException extends RuntimeException {
    public OrderDetailNotFoundException(String message){

        super(message);
    }
}
