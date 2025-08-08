package com.first.carRepairShop.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalTime;
import java.util.List;

public class InvalidAppointmentTimeResponse extends ErrorResponses {
    private final List<LocalTime> availableSlots;

    public InvalidAppointmentTimeResponse(HttpStatus status, String message,List<LocalTime> availableSlots) {
        super(status, message);
        this.availableSlots=availableSlots;
    }

    public List<LocalTime> getAvailableSlots() {
        return availableSlots;
    }
}
