package com.ecommerce.prototype.application.usecase.exception;

public class PaymentProcessingException extends RuntimeException {
    public PaymentProcessingException(String message){

        super(message);
    }
}
