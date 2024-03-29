package com.snews.server.controllerAdvice;

import com.snews.server.exceptions.*;
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
    public String handleUserAlreadyRegisteredException(Exception e) {
        return e.getMessage();
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MalformedDataException.class)
    public HashMap<String, String[]> handleMalformedDataException(Exception e) {
        HashMap<String, String[]> response = new HashMap<>();
        response.put("message", Arrays.stream(e.getMessage().split(System.lineSeparator())).sorted().toArray(String[]::new));
        return response;
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidPasswordResetException.class)
    public String handleInvalidPasswordResetException(Exception e) {
        return e.getMessage();
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerErrorException.class)
    public String handleInternalServerErrorException(Exception e) {
        return e.getMessage();
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NonExistentUserException.class)
    public String handleNonExistentUserException(Exception e) {
        return e.getMessage();
    }
}
