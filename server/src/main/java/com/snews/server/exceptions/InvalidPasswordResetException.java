package com.snews.server.exceptions;

public class InvalidPasswordResetException extends Exception {
    public InvalidPasswordResetException(String message) {
        super(message);
    }
}
