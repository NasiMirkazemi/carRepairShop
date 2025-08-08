package com.first.carRepairShop.exception;

public class EnumValidationError extends RuntimeException{
    public EnumValidationError(String message){
        super(message);
    }
}
