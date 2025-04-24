package com.aytaj.wellbeing.exception;

public class UserRegisteredBeforeException extends RuntimeException {
    public UserRegisteredBeforeException(String message) {
        super(message);
    }
}
