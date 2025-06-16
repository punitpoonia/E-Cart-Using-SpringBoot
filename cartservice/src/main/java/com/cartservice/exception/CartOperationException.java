package com.cartservice.exception;

public class CartOperationException extends RuntimeException {
    public CartOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
