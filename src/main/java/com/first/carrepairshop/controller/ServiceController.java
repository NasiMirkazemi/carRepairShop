package com.first.carrepairshop.controller;

import com.first.carrepairshop.dto.ServicesDto;
import com.first.carrepairshop.service.ServicesService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/serviceController")
@RequiredArgsConstructor
public class ServiceController {
    private final ServicesService servicesService;

    @PostMapping("/add")
    public ResponseEntity<ServicesDto> add(@RequestBody ServicesDto servicesDto) {
        return ResponseEntity.ok(servicesService.addService(servicesDto));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ServicesDto> get(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(servicesService.getService(id));
    }

    @PatchMapping("/update")
    public ResponseEntity<ServicesDto> update(@RequestBody ServicesDto servicesDto) {
        return ResponseEntity.ok(servicesService.updateService(servicesDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        servicesService.deleteService(id);
        return ResponseEntity.ok("service by :" + id + " is deleted ");

    }
}
