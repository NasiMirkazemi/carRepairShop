package com.first.carrepairshop.controller;

import com.first.carrepairshop.dto.CarDto;
import com.first.carrepairshop.dto.CustomerDto;
import com.first.carrepairshop.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carController")
@AllArgsConstructor
public class CarController {
    private final CarService carService;


    @PostMapping("/add")
    public ResponseEntity<CarDto> addCar(@RequestBody CarDto carDto) {
        return ResponseEntity.ok(carService.addCar(carDto));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getCar(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(carService.getCar(id));
    }

    @PatchMapping("/update")
    public ResponseEntity<CarDto> updateCar(@RequestBody CarDto carDto) {
        return ResponseEntity.ok(carService.updateCar(carDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable("id") Integer id) {
        carService.deleteCar(id);
        return ResponseEntity.ok("cars whit id :" + id + "is deleted");
    }

    @PostMapping("/addCustomerToCar/{carId}")
    public ResponseEntity<String> addCustomerToCar(@PathVariable("carId") Integer carId, @RequestBody CustomerDto customerDto) {
        carService.addCustomerToCar(carId, customerDto);
        return ResponseEntity.ok("customer whit id:" + customerDto.getName() + " is added to car whit id:" + carId);
    }
}
