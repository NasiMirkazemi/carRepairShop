package com.first.carrepairshop.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotfoundException.class)
    public ResponseEntity<?>handlerNotFoundException(NotfoundException notfoundException){
         return ResponseEntity.ok("error: " + notfoundException.getMessage());
    }
    @ExceptionHandler(CarNotfoundException.class)
    public ResponseEntity<?>handlerCarNotFoundException(CarNotfoundException CarNotfoundException){
        return ResponseEntity.ok("error"+CarNotfoundException.getMessage());
    }
}
