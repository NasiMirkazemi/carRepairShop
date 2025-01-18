package com.first.carrepairshop.service;

import com.first.carrepairshop.dto.CarDto;
import com.first.carrepairshop.dto.CustomerDto;
import com.first.carrepairshop.entity.Car;
import com.first.carrepairshop.entity.Customer;
import com.first.carrepairshop.exception.IllegalException;
import com.first.carrepairshop.exception.NotfoundException;
import com.first.carrepairshop.mapper.CarMapper;
import com.first.carrepairshop.mapper.CustomerMapper;
import com.first.carrepairshop.repository.CarRepository;
import com.first.carrepairshop.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CarMapper carMapper;


    public CarDto addCar(CarDto carDto) {
        if (carDto == null) {
            throw new IllegalException("carDto cannot be null");
        }
        Car carEntity = carRepository.save(carMapper.toCarEntity(carDto));
        return carMapper.toCarDto(carEntity);

    }

    public CarDto getCar(Integer id) {
        Car carEntity = carRepository.findById(id).orElseThrow(() -> new NotfoundException("car not found whit id:" + id));
        return carMapper.toCarDto(carEntity);
    }

    public CarDto updateCar(CarDto carDto) {
        Car carEntity = carRepository.findById(carDto.getCarId())
                .orElseThrow(() -> new NotfoundException("car Not found whit id:" + carDto.getCarId()));
        carEntity.setNumberPlate(Optional.ofNullable(carDto.getNumberPlate()).orElse(carEntity.getNumberPlate()));
        carEntity.setMake(Optional.ofNullable(carDto.getMake()).orElse(carEntity.getMake()));
        carEntity.setModel(Optional.ofNullable(carDto.getModel()).orElse(carEntity.getModel()));
        carEntity.setYear(Optional.ofNullable(carDto.getYear()).orElse(carEntity.getYear()));
        if (carDto.getCustomerDto() != null) {
            Customer customer = customerRepository.findById(carDto.getCustomerDto().getCustomerId())
                    .orElseThrow(() -> new NotfoundException("Customer not found wjit id:" + carDto.getCustomerDto().getCustomerId()));
            carEntity.setCustomer(customer);
        } else {
            carEntity.setCustomer(null);
        }
        carEntity = carRepository.save(carEntity);
        return carMapper.toCarDto(carEntity);
    }

    public void deleteCar(Integer carId) {
        Car carEntity = carRepository.findById(carId)
                .orElseThrow(() -> new NotfoundException("car not found whit id:" + carId));
        carRepository.delete(carEntity);
    }

    @Transactional
    public void addCustomerToCar(Integer carId, CustomerDto customerDto) {
        Car retriveCar = carRepository.findById(carId)
                .orElseThrow(() -> new NotfoundException("car not found whit id: " + carId));
        Customer customer = customerRepository.findCustomerByName(customerDto.getName())
                .orElseGet(() -> {
                    Customer customerEntity = customerMapper.toCustomerEntity(customerDto);
                    return customerRepository.save(customerEntity);
                });
        retriveCar.setCustomer(customer);
        customer.getCars().add(retriveCar);
        carRepository.save(retriveCar);
        customerRepository.save(customer);


    }

}
