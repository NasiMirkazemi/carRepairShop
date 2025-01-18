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
    public ResponseEntity<?>handlerCarNotFoundException(CarNotfoundException carNotfoundException){
        return ResponseEntity.ok("error: "+carNotfoundException.getMessage());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handlerIllegalException(IllegalArgumentException illegalArgumentException){
        return ResponseEntity.ok("error: "+illegalArgumentException.getMessage());
    }
}

