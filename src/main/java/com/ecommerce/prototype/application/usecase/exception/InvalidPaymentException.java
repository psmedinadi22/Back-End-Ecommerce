package com.ecommerce.prototype.application.usecase.exception;

public class InvalidPaymentException extends RuntimeException {
    public InvalidPaymentException(String message){

        super(message);
    }
}
