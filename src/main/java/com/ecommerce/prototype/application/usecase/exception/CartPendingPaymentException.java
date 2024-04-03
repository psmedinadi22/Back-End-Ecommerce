package com.ecommerce.prototype.application.usecase.exception;

public class CartPendingPaymentException extends RuntimeException {
    public CartPendingPaymentException(String message){

        super(message);
    }
}
