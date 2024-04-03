package com.ecommerce.prototype.application.usecase.exception;

public class ProductAlreadyInCartException extends RuntimeException {
    public ProductAlreadyInCartException(String message){

        super(message);
    }

}