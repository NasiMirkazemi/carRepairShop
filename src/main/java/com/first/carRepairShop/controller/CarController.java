package com.first.carRepairShop.controller;

import com.first.carRepairShop.dto.CarDto;
import com.first.carRepairShop.services.impl.CarService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
@AllArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping("/add")
    public ResponseEntity<CarDto> addCar(@Valid @RequestBody CarDto carDto) {
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

    @PostMapping("/addCustomerToCar/{carId}/{customerId}")
    public ResponseEntity<String> changeCarOwner(@PathVariable("carId") Integer carId, @PathVariable Integer customerId) {
        carService.changeCarOwner(carId, customerId);
        return ResponseEntity.ok("customer whit id:" + customerId + " is added to car whit id:" + carId);
    }

    @GetMapping("/getCarByNumberPlate/{numberPlate}")
    public ResponseEntity<CarDto> getCarByNumberPlate(@PathVariable("numberPlate") String numberPlate) {
        CarDto carDto = carService.getCarByNumberPlate(numberPlate);
        return ResponseEntity.ok(carDto);
    }

    @GetMapping("/getCarsByModel/{model}")
    public ResponseEntity<List<CarDto>> getCarsByModel(@PathVariable("model") String model) {
        List<CarDto> carDtoList = carService.findCarsByModel(model);
        return ResponseEntity.ok(carDtoList);
    }

    @GetMapping("/getAllCars")
    public ResponseEntity<List<CarDto>> getAllCars() {
        List<CarDto> carDtoList = carService.getAllCar();
        return ResponseEntity.ok(carDtoList);
    }
}
