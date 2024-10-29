package com.first.carrepairshop.controller;

import com.first.carrepairshop.dto.ServicesDto;
import com.first.carrepairshop.service.ServicesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/serviceController")
@AllArgsConstructor
public class ServiceController {
    private final ServicesService servicesService;
    @PostMapping("/add")
    public ResponseEntity<ServicesDto> addService(@RequestBody ServicesDto servicesDto){
        return  ResponseEntity.ok(servicesService.add(servicesDto));
    }
    @GetMapping("/get/{serviceId}")
    public ResponseEntity<ServicesDto> getService(@PathVariable("serviceId") Integer id){
        return ResponseEntity.ok(servicesService.getById(id));
    }
}
