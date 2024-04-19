package com.ecommerce.prototype.application.usecase.exception;

public class TokenizedCardNotFoundException extends RuntimeException {
    public TokenizedCardNotFoundException(String message) {
        super(message);
    }
}
