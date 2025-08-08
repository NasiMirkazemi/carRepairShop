package com.first.carRepairShop.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data

public class ErrorResponses {
    private int status;
    private String error;
    private String message;
    private Instant timestamp;

    public ErrorResponses(HttpStatus status, String message) {
        this.status = status.value(); // Converts HttpStatus to an int (e.g., HttpStatus.NOT_FOUND -> 404)
        this.error = status.getReasonPhrase(); // Converts HttpStatus to a string (e.g., "Not Found")
        this.message = message; // Custom error message
        this.timestamp = Instant.now(); // Sets the current time
    }


}
