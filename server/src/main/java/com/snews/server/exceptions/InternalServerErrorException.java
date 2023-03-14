package com.snews.server.exceptions;

public class InternalServerErrorException extends Exception {
    public InternalServerErrorException(String message) {
        super(message);
    }
}
