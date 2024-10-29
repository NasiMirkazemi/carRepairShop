package com.first.carrepairshop.service;

import com.first.carrepairshop.dto.CarDto;
import com.first.carrepairshop.entity.Car;
import com.first.carrepairshop.repository.CarRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class CarService {
    private final CarRepository carRepository;




    public CarDto addCar(CarDto carDto) {
        Car car = carRepository.save(Car.builder()
                .carId(carDto.getCarId())
                .numberPlate(carDto.getNumberPlate())
                .model(carDto.getModel())
                .make(carDto.getMake())
                .year(carDto.getYear())
                .customer(carDto.getCustomer())
                .build());
        carDto.setCarId(car.getCarId());
        return carDto;

    }

    public CarDto getCar(Integer carId) {
        Car car = carRepository.findById(carId).get();
        return CarDto.builder()
                .CarId(car.getCarId())
                .numberPlate(car.getNumberPlate())
                .model(car.getModel())
                .make(car.getMake())
                .year(car.getYear())
                .customer(car.getCustomer())
                .build();
    }

    public void deleteUserById(Integer id) {
        carRepository.deleteById(id);
        System.out.println("user by" + id + "deleted");
    }

    public CarDto update(CarDto carDto) {
        Car car = carRepository.save(Car.builder()
                .carId(carDto.getCarId())
                .numberPlate(carDto.getNumberPlate())
                .model(carDto.getModel())
                .make(carDto.getMake())
                .year(carDto.getYear())
                .customer(carDto.getCustomer())
                .build());
        return CarDto.builder()
                .CarId(car.getCarId())
                .numberPlate(car.getNumberPlate())
                .model(car.getModel())
                .make(car.getMake())
                .year(car.getYear())
                .customer(car.getCustomer())
                .build();


    }

}
