package com.first.carrepairshop.service;

import com.first.carrepairshop.dto.CarDto;
import com.first.carrepairshop.entity.Car;
import com.first.carrepairshop.entity.Customer;
import com.first.carrepairshop.exception.NotfoundException;
import com.first.carrepairshop.repository.CarRepository;
import com.first.carrepairshop.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CarService {
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;


    public CarDto addCar(CarDto carDto) {
        Car carEntity = carRepository.save(Car.builder()
                .carId(carDto.getCarId())
                .numberPlate(carDto.getNumberPlate())
                .model(carDto.getModel())
                .make(carDto.getMake())
                .year(carDto.getYear())
                .customer(carDto.getCustomer())
                .build());
        carDto.setCarId(carEntity.getCarId());
        return carDto;

    }

    public CarDto getCar(Integer id) {
        Car carEntity = carRepository.findById(id).get();
        return CarDto.builder()
                .carId(carEntity.getCarId())
                .numberPlate(carEntity.getNumberPlate())
                .model(carEntity.getModel())
                .make(carEntity.getMake())
                .year(carEntity.getYear())
                .customer(carEntity.getCustomer())
                .build();
    }

    public CarDto updateCar(CarDto carDto) {
        Optional<Car> carOptional = carRepository.findById(carDto.getCarId());
        Car carEntity = null;
        if (carOptional.isPresent()) {
            carEntity = carOptional.get();
            if (carDto.getNumberPlate() != null)
                carEntity.setNumberPlate(carDto.getNumberPlate());
            if (carDto.getMake() != null)
                carEntity.setMake(carDto.getMake());
            if (carDto.getMake() != null)
                carEntity.setModel(carDto.getModel());
            if (carDto.getYear() != null)
                carEntity.setYear(carDto.getYear());
            if (carDto.getCustomer() != null) {
                Optional<Customer> customerOptional = customerRepository.findById(carDto.getCustomer().getCustomerId());
                if (!customerOptional.isPresent()) {
                    throw new NotfoundException("Customer not found");
                }
                Customer customer = customerOptional.get();
                carEntity.setCustomer(carDto.getCustomer());
            } else {
                carEntity.setCustomer(null);
            }

            carRepository.save(carEntity);
        }
        return CarDto.builder()
                .carId(carEntity.getCarId())
                .numberPlate(carEntity.getNumberPlate())
                .model(carEntity.getModel())
                .make(carEntity.getMake())
                .year(carEntity.getYear())
                .customer(carEntity.getCustomer())
                .build();
    }

    public void deleteCar(Integer id) {
        carRepository.deleteById(id);
    }

}
