package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.dto.CarDto;
import com.first.carRepairShop.dto.CustomerRegister;
import com.first.carRepairShop.entity.Car;
import com.first.carRepairShop.entity.Customer;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.mapper.CarMapper;
import com.first.carRepairShop.mapper.CustomerMapper;
import com.first.carRepairShop.repository.CarRepository;
import com.first.carRepairShop.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CarMapper carMapper;
    private final Logger logger = LoggerFactory.getLogger(CarService.class);


    // 🔹 Convert CarDto → Car Entity (Handles Customer manually)
    // Convert Car-> CarDto (Handles in CarMapper)
    private Car toCarEntity1(CarDto carDto) {
        Car car = carMapper.toCarEntity(carDto);
        if (carDto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(carDto.getCustomerId())
                    .orElseThrow(() -> new EntityNotFoundException("Customer with this Id not found:" + carDto.getCustomerId()));
            car.setCustomer(customer);
            customer.getCars().add(car);

        }
        return car;
    }

    @Transactional
    public CarDto addCar(CarDto carDto) {
        if (carDto == null) {
            throw new BadRequestException("carDto cannot be null");
        }
        if (carDto.getCustomerId() == null) {
            throw new BadRequestException("Customer id is required");
        }
        Customer customer = customerRepository.findById(carDto.getCustomerId())
                .orElseThrow(() -> new NotFoundException("No Customer find with id:" + carDto.getCustomerId()));
        Car car = toCarEntity1(carDto);
        car.setCreateAt(LocalDate.now());
        customer.getCars().add(car);

        Car savedCar = carRepository.save(car);
        return carMapper.toCarDto(savedCar);
    }

    public CarDto getCar(Integer carId) {
        Car carEntity = carRepository.findById(carId).orElseThrow(() ->
                new NotFoundException("car not found whit id:" + carId));
        return carMapper.toCarDto(carEntity);
    }

    @Transactional
    public CarDto updateCar(CarDto carDto) {
        if (carDto == null || carDto.getCarId() == null) {
            throw new BadRequestException("carDto or carId cannot be null");
        }
        Car carEntity = carRepository.findById(carDto.getCarId())
                .orElseThrow(() -> new NotFoundException("car Not found with id:" + carDto.getCarId()));
        Optional.ofNullable(carDto.getMake()).ifPresent(make -> carEntity.setMake(make));
        Optional.ofNullable(carDto.getModel()).ifPresent(model -> carEntity.setModel(model));
        Optional.ofNullable(carDto.getYear()).ifPresent(year -> carEntity.setYear(year));//(carEntity::setYear)
        Optional.ofNullable(carDto.getNumberPlate()).ifPresent(numberPlate -> carEntity.setNumberPlate(numberPlate));
        carRepository.save(carEntity);
        return carMapper.toCarDto(carEntity);
    }

    public CustomerRegister updateCustomerForCar(Integer carId, CustomerRegister customerRegister) {
        if (carId == null) {
            throw new BadRequestException("Car Id is required");
        }
        if (customerRegister == null) {
            throw new BadRequestException("Customer is  required");
        }
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("No car found with id:" + carId));

        Customer customer = car.getCustomer();
        if (!customer.getId().equals(customerRegister.getId())) {
            throw new IllegalStateException("Car with ID "
                    + carId + " does not belong to customer with ID "
                    + customerRegister.getId());
        }
        Optional.ofNullable(customerRegister.getCustomerNumber()).ifPresent(customerNumber -> customer.setCustomerNumber(customerNumber));
        Optional.ofNullable(customerRegister.getUser().getName()).ifPresent(customer::setName);
        Optional.ofNullable(customerRegister.getUser().getLastname()).ifPresent(customer::setLastname);
        Optional.ofNullable(customerRegister.getUser().getEmail()).ifPresent(customer::setEmail);
        Optional.ofNullable(customerRegister.getUser().getPhone()).ifPresent(customer::setPhone);
        Optional.ofNullable(customerRegister.getUser().getAddress()).ifPresent(customer::setAddress);
        customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    @Transactional
    public CarDto changeCarOwner(Integer carId, Integer customerId) {
        if (carId == null || customerId == null) {
            throw new BadRequestException("car id and customer id is required");
        }
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("No car found with id:" + carId));
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("No Customer found with id:" + customerId));
        car.setCustomer(customer);
        // customer.getCars().add(car);
        Car changedCar = carRepository.save(car);
        return carMapper.toCarDto(changedCar);

    }

    public void deleteCar(Integer carId) {
        if (carId == null) {
            throw new BadRequestException("car id is required");
        }
        Car carEntity = carRepository.findById(carId).orElseThrow(() -> new NotFoundException("No car found with id:" + carId));
        carRepository.delete(carEntity);
        System.out.println("car has been deleted with id:" + carId);
    }

    public CarDto getCarByNumberPlate(String numberPlate) {
        if (numberPlate == null || !numberPlate.isBlank()) {
            throw new BadRequestException("number plate is required");
        }
        Car carEntity = carRepository.findByNumberPlate(numberPlate)
                .orElseThrow(() -> new NotFoundException("car not found with number plate:" + numberPlate));
        return carMapper.toCarDto(carEntity);
    }

    public List<CarDto> findCarsByModel(String model) {
        if (model == null || model.isBlank()) {
            throw new BadRequestException("model cannot be null");
        }
        List<Car> carList = carRepository.findByModel(model);
        if (carList.isEmpty()) {
            throw new NotFoundException("no car found whit this model :" + model);
        }
        return carList.stream()
                .map(carMapper::toCarDto)//(cardto->carMapper.toCarDto(carDto));
                .collect(Collectors.toList());
    }

    public List<CarDto> getAllCar() {
        List<Car> cars = carRepository.findAll();
        return cars.stream()
                .map(carMapper::toCarDto)
                .collect(Collectors.toList());

    }

}
