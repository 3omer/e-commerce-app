package com.omosman.ecommerce.exception;

public class InsufficientProductQuantityException extends RuntimeException {
    public InsufficientProductQuantityException(String msg) {
        super(msg);
    }
}
