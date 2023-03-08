package com.snews.server.exceptions;

import org.springframework.validation.BindingResult;

public class MalformedDataException extends Exception {
    public MalformedDataException(String message) {
        super(message);
    }
}
