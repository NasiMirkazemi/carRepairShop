package com.first.carRepairShop.exception;

public class InvalidWorkLogStatusException extends RuntimeException{
    public InvalidWorkLogStatusException(String message){
        super(message);
    }
}
