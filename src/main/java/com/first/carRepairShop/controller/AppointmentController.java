package com.first.carRepairShop.controller;

import com.first.carRepairShop.dto.AppointmentDto;
import com.first.carRepairShop.services.impl.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/appointmentController")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PutMapping("/confirm/{appointmentNumber}")
    public ResponseEntity<AppointmentDto> confirmAppointment(@PathVariable("appointmentNumber") String appointmentNumber) {
        AppointmentDto appointmentDto = appointmentService.confirmAppointmentManually(appointmentNumber);
        return ResponseEntity.ok(appointmentDto);
    }
    @GetMapping("/autoConfirm")
    public ResponseEntity<String> autoConfirmAppointment(){
        appointmentService.autoConfirmAppointment();
        return ResponseEntity.ok("auto confirmation is done");

    }

    @GetMapping("/availableSlotTimes")
    public ResponseEntity<List<LocalTime>> getAvailableTimeSlots(LocalDate appointmentDate) {
        List<LocalTime> availableTimeSlots = appointmentService.getAvailableTimeSlots(appointmentDate);
        return ResponseEntity.ok(availableTimeSlots);
    }

    @GetMapping("/GenerateAllTimeSlot")
    public ResponseEntity<List<LocalTime>> generateTimeSlots() {
        List<LocalTime> allTimeSlots = appointmentService.generateTimeSlots();
        return ResponseEntity.ok(allTimeSlots);
    }

    @PutMapping("/cancelAppointment/{appointmentNumber}")
    public ResponseEntity<AppointmentDto> cancelAppointment(@PathVariable("appointmentNumber") String appointmentNumber) {
        AppointmentDto appointmentDto = appointmentService.cancelAppointment(appointmentNumber);
        return ResponseEntity.ok(appointmentDto);
    }


    @PutMapping("/updateAppointment")
    public ResponseEntity<AppointmentDto> updateAppointment(@RequestBody AppointmentDto appointmentDto) {
        AppointmentDto appointmentDto1 = appointmentService.updateAppointment(appointmentDto);
        return ResponseEntity.ok(appointmentDto1);
    }

    @GetMapping("/getById/{appointmentId}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable("appointmentId") Integer appointmentId) {
        AppointmentDto appointmentDto = appointmentService.getAppointmentById(appointmentId);
        return ResponseEntity.ok(appointmentDto);
    }

    @GetMapping("/getAllAppointments")
    public ResponseEntity<List<AppointmentDto>> getAllAppointments() {
        List<AppointmentDto> appointmentDtoList = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointmentDtoList);
    }

    @GetMapping("/getAllByCustomerId/{customerId}")
    public ResponseEntity<List<AppointmentDto>> getAllByCustomerId(@PathVariable("customerId") Integer customerId) {
        List<AppointmentDto> appointmentDtoList = appointmentService.getAllByCustomerId(customerId);
        return ResponseEntity.ok(appointmentDtoList);
    }

    @GetMapping("/getAllByDate/{date}")
    public ResponseEntity<List<AppointmentDto>> findAllByDate(@PathVariable("date") LocalDate date) {
        List<AppointmentDto> appointmentDtoList = appointmentService.getAppointmentsByDate(date);
        return ResponseEntity.ok(appointmentDtoList);
    }
}
