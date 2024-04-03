package com.ecommerce.prototype.application.usecase.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message){

        super(message);
    }

}
