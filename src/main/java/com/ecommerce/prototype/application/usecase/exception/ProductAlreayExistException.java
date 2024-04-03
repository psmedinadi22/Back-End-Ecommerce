package com.ecommerce.prototype.application.usecase.exception;

public class ProductAlreayExistException extends RuntimeException {
    public ProductAlreayExistException(String message){

        super(message);
    }

}
