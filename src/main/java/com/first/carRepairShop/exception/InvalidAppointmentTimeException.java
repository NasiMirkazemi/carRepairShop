package com.first.carRepairShop.exception;

import lombok.RequiredArgsConstructor;

import java.time.LocalTime;
import java.util.List;
@RequiredArgsConstructor
public class InvalidAppointmentTimeException extends RuntimeException {
    private final List<LocalTime> availableSlots;

    public InvalidAppointmentTimeException(String message, List<LocalTime> availableSlots) {
        super(message);
        this.availableSlots = availableSlots;
    }

    public List<LocalTime> getAvailableSlots() {
        return availableSlots;
    }
}
