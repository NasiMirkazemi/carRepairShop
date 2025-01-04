package com.first.carrepairshop.service;

import com.first.carrepairshop.dto.CarDto;
import com.first.carrepairshop.entity.Car;
import com.first.carrepairshop.entity.Customer;
import com.first.carrepairshop.exception.NotfoundException;
import com.first.carrepairshop.mapper.CarMapper;
import com.first.carrepairshop.mapper.CustomerMapper;
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
    private final CustomerMapper customerMapper;
    private final CarMapper carMapper;


    public CarDto addCar(CarDto carDto) {
        Car carEntity = carRepository.save(carMapper.toCarEntity(carDto));
        return carMapper.toCarDto(carEntity);

    }

    public CarDto getCar(Integer id) {
        Car carEntity = carRepository.findById(id).get();
        return carMapper.toCarDto(carEntity);
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
            if (carDto.getCustomerDto() != null) {
                Optional<Customer> customerOptional = customerRepository.findById(carDto.getCustomerDto().getCustomerId());
                if (!customerOptional.isPresent()) {
                    throw new NotfoundException("Customer not found");
                }
                Customer customer = customerOptional.get();
                carEntity.setCustomer(customerMapper.toCustomerEntity(carDto.getCustomerDto()));
            } else {
                carEntity.setCustomer(null);
            }

            carRepository.save(carEntity);
        }
        return carMapper.toCarDto(carEntity);
    }

    public void deleteCar(Integer id) {
    carRepository.deleteById(id);
    }

}
