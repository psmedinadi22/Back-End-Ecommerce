package com.ecommerce.prototype.application.usecase.exception;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(String message){

        super(message);
    }
}
