package com.ecommerce.prototype.application.usecase.exception;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String message){

        super(message);
    }
}
