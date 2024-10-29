package com.first.carrepairshop.controller;

import com.first.carrepairshop.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carController")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping("/echo/{msg}")
    public ResponseEntity<String> echo(@PathVariable String msg){
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/getCar/{id}")
    public ResponseEntity<?> getCar(@PathVariable Integer id) {
        return ResponseEntity.ok(carService.getCar(id));
    }
}
