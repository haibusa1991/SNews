package com.snews.server.controllerAdvice;

import com.snews.server.exceptions.UserAlreadyRegisteredException;
import com.snews.server.exceptions.MalformedDataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;

@RestControllerAdvice
public class ExceptionResolver {

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public HashMap<String, String> handleEmailAlreadyRegisteredException(Exception e) {
        HashMap<String, String> response = new HashMap<>();
        response.put("message", e.getMessage());
        return response;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MalformedDataException.class)
    public HashMap<String, String[]> handleMalformedDataException(Exception e) {
        HashMap<String, String[]> response = new HashMap<>();
        response.put("message", Arrays.stream(e.getMessage().split(System.lineSeparator())).sorted().toArray(String[]::new));
        return response;
    }
}
