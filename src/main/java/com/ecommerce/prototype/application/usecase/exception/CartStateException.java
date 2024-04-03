package com.ecommerce.prototype.application.usecase.exception;

public class CartStateException extends RuntimeException {
    public CartStateException(String message){

        super(message);
    }
}
