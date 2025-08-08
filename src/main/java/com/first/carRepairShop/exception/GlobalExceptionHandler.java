package com.first.carRepairShop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handlerNotFoundException(NotFoundException notfoundException) {
        ErrorResponses error = new ErrorResponses(HttpStatus.NOT_FOUND, notfoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<?> handlerCarNotFoundException(CarNotFoundException carNotfoundException) {
        ErrorResponses error = new ErrorResponses(HttpStatus.NOT_FOUND, carNotfoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handlerIllegalException(IllegalArgumentException illegalArgumentException) {
        ErrorResponses error = new ErrorResponses(HttpStatus.BAD_REQUEST, illegalArgumentException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EnumValidationError.class)
    public ResponseEntity<?> handlerEnumValidationError(EnumValidationError enumValidationError) {
        ErrorResponses error = new ErrorResponses(HttpStatus.BAD_REQUEST, enumValidationError.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(InvalidAppointmentTimeException.class)
    public ResponseEntity<?> handelInvalidAppointmentTimeException(InvalidAppointmentTimeException invalidAppointmentTimeException) {
        InvalidAppointmentTimeResponse error =
                new InvalidAppointmentTimeResponse(
                        HttpStatus.BAD_REQUEST,
                        invalidAppointmentTimeException.getMessage(),
                        invalidAppointmentTimeException.getAvailableSlots());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<?> handelInvalidDateRangException(InvalidDateRangeException invalidDateRangeException) {
        ErrorResponses error = new ErrorResponses(HttpStatus.BAD_REQUEST,
                invalidDateRangeException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(InvalidWorkLogStatusException.class)
    public ResponseEntity<?> handelInvalidWorkLogStatusException(InvalidWorkLogStatusException invalidWorkLogStatusException) {
        ErrorResponses error = new ErrorResponses(HttpStatus.BAD_REQUEST,
                invalidWorkLogStatusException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExistException(UserAlreadyExistsException userAlreadyExistsException) {
        ErrorResponses error = new ErrorResponses(HttpStatus.CONFLICT,
                userAlreadyExistsException.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);

    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentialsException(InvalidCredentialsException invalidCredentialsException) {
        ErrorResponses error = new ErrorResponses(HttpStatus.UNAUTHORIZED,
                invalidCredentialsException.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(RoleAlreadyExistException.class)
    public ResponseEntity<?> handelRoleAlreadyException(RoleAlreadyExistException roleAlreadyExistException) {
        ErrorResponses error = new ErrorResponses(HttpStatus.CONFLICT,
                roleAlreadyExistException.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(PermissionExistException.class)
    public ResponseEntity<?> handelPermissionExistException(PermissionExistException permissionExistException) {
        ErrorResponses error = new ErrorResponses(HttpStatus.CONFLICT, permissionExistException.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(TokenValidationException.class)
    public ResponseEntity<?> handleTokenValidationException(TokenValidationException tokenValidationException) {
        ErrorResponses error = new ErrorResponses(HttpStatus.UNAUTHORIZED, tokenValidationException.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException businessException) {
        ErrorResponses error = new ErrorResponses(HttpStatus.BAD_REQUEST, businessException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(BusinessConflictException.class)
    public ResponseEntity<?> handleBusinessConflictException(BusinessConflictException businessConflictException) {
        ErrorResponses error = new ErrorResponses(HttpStatus.CONFLICT, businessConflictException.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);

    }


}

