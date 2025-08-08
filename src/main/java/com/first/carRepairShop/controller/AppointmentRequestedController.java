package com.first.carRepairShop.controller;

import com.first.carRepairShop.dto.AppointmentDto;
import com.first.carRepairShop.services.impl.AppointmentRequestRedisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/AppointmentRequestedController")
@RequiredArgsConstructor
public class AppointmentRequestedController {
    private final AppointmentRequestRedisService appointmentRequestRedisService;

    @PostMapping("/reserve")
    public ResponseEntity<AppointmentDto> reserveAppointment(@RequestBody @Valid AppointmentDto appointmentDto) {
        AppointmentDto appointmentDto1 = appointmentRequestRedisService.reserveAppointmentRequest(appointmentDto);
        return ResponseEntity.ok(appointmentDto1);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AppointmentDto>> getAllRequestedAppointments() {
        List<AppointmentDto> appointmentDtoList = appointmentRequestRedisService.getAllRequestedAppointments();
        return ResponseEntity.ok(appointmentDtoList);
    }

    @GetMapping("/getByAppointment/")
    public ResponseEntity<AppointmentDto> getRequestedAppointment(@RequestParam("appointmentNumber") String appointmentNumber) {
        AppointmentDto appointmentDto = appointmentRequestRedisService.getAppointmentByNumber(appointmentNumber);
        return ResponseEntity.ok(appointmentDto);
    }
    @DeleteMapping("/removeByNumber")
    public ResponseEntity<String> removeRequestedAppointment(@RequestParam("appointmentNumber") String appointmentNumber){
         String message=appointmentRequestRedisService.removeRequestedAppointment(appointmentNumber);
         return ResponseEntity.ok(message);
    }
    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllRequestedAppointment(){
        appointmentRequestRedisService.deleteAllRequestedAppointment();
       return ResponseEntity.ok("All Requested appointment has been deleted");
    }
}
