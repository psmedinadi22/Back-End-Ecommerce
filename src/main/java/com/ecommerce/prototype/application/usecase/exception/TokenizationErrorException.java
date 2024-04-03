package com.ecommerce.prototype.application.usecase.exception;

public class TokenizationErrorException extends RuntimeException {
    public TokenizationErrorException(String message) {
        super(message);
    }
}
