package com.example.exchanger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SimpleAdviceController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<String> handleConflictParameterException(NotEnoughMoneyException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
