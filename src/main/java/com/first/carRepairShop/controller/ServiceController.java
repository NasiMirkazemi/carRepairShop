package com.first.carRepairShop.controller;

import com.first.carRepairShop.dto.ServiceDto;
import com.first.carRepairShop.services.impl.ServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServiceController {


    private final ServicesService servicesService;

    @PostMapping("/add")
    public ResponseEntity<ServiceDto> add(@RequestBody ServiceDto servicesDto) {
        return ResponseEntity.ok(servicesService.addService(servicesDto));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ServiceDto> get(@PathVariable("id") Integer serviceId) {
        return ResponseEntity.ok(servicesService.getService(serviceId));
    }

    @PatchMapping("/update")
    public ResponseEntity<ServiceDto> update(@RequestBody ServiceDto servicesDto) {
        return ResponseEntity.ok(servicesService.updateService(servicesDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        servicesService.deleteService(id);
        return ResponseEntity.ok("service by :" + id + " is deleted ");

    }
}
