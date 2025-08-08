package com.first.carRepairShop.exception;

public class PermissionExistException extends RuntimeException {
    public PermissionExistException(String message) {
        super(message);
    }
}
