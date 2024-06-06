package com.blackfield.StockManagement.exception;

import lombok.Getter;

public class BadCredendialException extends RuntimeException {

    @Getter
    private String language;

    public BadCredendialException(String message) {
        super(message);
    }

    public BadCredendialException(String message, String language) {
        super(message);
        this.language = language;
    }
}
