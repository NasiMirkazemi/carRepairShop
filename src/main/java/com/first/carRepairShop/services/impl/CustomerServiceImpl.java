package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.dto.CarDto;
import com.first.carRepairShop.dto.CustomerRegister;
import com.first.carRepairShop.dto.CustomerDtoWithCarList;
import com.first.carRepairShop.dto.RepairOrderDto;
import com.first.carRepairShop.entity.Car;
import com.first.carRepairShop.entity.Customer;
import com.first.carRepairShop.entity.RepairOrder;
import com.first.carRepairShop.entity.Role;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.exception.UserAlreadyExistsException;
import com.first.carRepairShop.mapper.CarMapper;
import com.first.carRepairShop.mapper.CustomerMapper;
import com.first.carRepairShop.mapper.RepairOrderMapper;
import com.first.carRepairShop.repository.*;
import com.first.carRepairShop.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;
    private final CustomerMapper customerMapper;
    private final CarMapper carMapper;
    private final RepairOrderMapper repairOrderMapper;
    private final RepairOrderRepository repairOrderRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Transactional
    public CustomerRegister addCustomer(CustomerRegister customerRegister) {
        if (customerRegister == null) {
            throw new BadRequestException("Customer is required");
        }

        if (userRepository.existsByUsername(customerRegister.getUser().getUsername())){
            throw new UserAlreadyExistsException("Username '"+ customerRegister.getUser().getUsername());
        }
        Role role = roleRepository.findByName(customerRegister.getUser().getRole().getName())
                .orElseThrow(() -> new NotFoundException("No Such Role found"));
        Customer customer=Customer.builder()
                .username(customerRegister.getUser().getUsername())
                .password(customerRegister.getUser().getPassword())
                .name(customerRegister.getUser().getName())
                .lastname(customerRegister.getUser().getLastname())
                .email(customerRegister.getUser().getEmail())
                .phone(customerRegister.getUser().getPhone())
                .address(customerRegister.getUser().getAddress())
                .role(role)
                .isEnable(true)
                .customerNumber(customerRegister.getCustomerNumber())
                .build();
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toDto(savedCustomer);
    }

    @Transactional
    public CustomerRegister updateCustomer(CustomerRegister customerRegister) {
        if (customerRegister == null || customerRegister.getId() == null) {
            throw new BadRequestException("Customer  and customer id are required");
        }
        Customer customer = customerRepository.findById(customerRegister.getId())
                .orElseThrow(() -> new NotFoundException("No customer found with id:" + customerRegister.getId()));
        Optional.ofNullable(customerRegister.getCustomerNumber()).ifPresent(customerNumber -> customer.setCustomerNumber(customerNumber));
        Optional.ofNullable(customerRegister.getUser().getName()).ifPresent(name -> customer.setName(name));
        Optional.ofNullable(customerRegister.getUser().getLastname()).ifPresent(lastname -> customer.setLastname(lastname));
        Optional.ofNullable(customerRegister.getUser().getEmail()).ifPresent(email -> customer.setEmail(email));
        Optional.ofNullable(customerRegister.getUser().getPhone()).ifPresent(phone -> customer.setPhone(phone));
        Optional.ofNullable(customerRegister.getUser().getAddress()).ifPresent(address -> customer.setAddress(address));
        customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    @Transactional
    public void deleteCustomerById(Integer customerId) {
        if (customerId == null) {
            throw new BadRequestException("customer id is required");
        }
        customerRepository.deleteById(customerId);
        logger.info("customer has been deleted with id:" + customerId);
    }

    @Transactional(readOnly = true)
    public CustomerRegister getCustomerById(Integer customerId) {
        if (customerId == null) {
            throw new BadRequestException("Customer id is required");
        }
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("No Customer found with id :" + customerId));
        logger.info("Retrieved customer with ID: {}", customerId);
        return customerMapper.toDto(customer);
    }

    public List<CustomerRegister> getCustomerByNameAndLastname(String name, String lastname) {
        if (name == null || lastname == null) {
            throw new BadRequestException("name and lastname is required");
        }
        List<Customer> customerList = customerRepository.findCustomerByNameAndLastname(name, lastname);
        return customerMapper.toCustomerDtoList(customerList);
    }

    public CustomerRegister findCustomerByPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new BadRequestException("phone number is required");
        }
        Customer customer = customerRepository.findByPhone(phoneNumber)
                .orElseThrow(() -> new NotFoundException("No customer found with phone number:" + phoneNumber));
        return customerMapper.toDto(customer);
    }

    @Transactional(readOnly = true)
    public List<CustomerRegister> findAllCustomer() {
        List<Customer> customerList = customerRepository.findAll();
        if (customerList.isEmpty()) {
            throw new NotFoundException("No customers found");
        }
        return customerMapper.toCustomerDtoList(customerList);
    }

    public List<CarDto> findCarsByCustomerId(Integer customerId) {
        if (customerId == null) {
            throw new BadRequestException("Customer id is required");
        }
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("No customer found with id:" + customerId));
        return Optional.ofNullable(customer.getCars())
                .orElse(Collections.emptyList())
                .stream()
                .map(carMapper::toCarDto)
                .collect(Collectors.toList());


    }

    @Transactional
    public CarDto addCarToCustomer(Integer customerId, CarDto carDto) {
        if (carDto == null) {
            throw new BadRequestException("Car is required");
        }
        if (customerId == null) {
            throw new BadRequestException("Customer id is required");
        }
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("No customer found with id:" + customerId));
        Car car = carMapper.toCarEntity(carDto);
        car.setCustomer(customer);
        customer.getCars().add(car);
        carRepository.save(car);
        customerRepository.save(customer);
        return carMapper.toCarDto(car);
    }

    @Transactional
    public void removeCarFromCustomer(Integer customerId, Integer carId) {
        if (carId == null) {
            throw new BadRequestException("Car id is required");
        }
        if (customerId == null) {
            throw new BadRequestException("Customer id is required");
        }
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("No customer found with id:" + customerId));
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("No car found with id:" + carId));
        if (!customer.getCars().contains(car)) {
            throw new IllegalStateException("Car with ID " + carId + " does not belong to customer with ID " + customerId);
        }
        customer.getCars().remove(car);
        customerRepository.save(customer);
        carRepository.delete(car);
        logger.info("Car has been deleted with ID: {}", carId);
    }

    @Transactional
    public CarDto updateCarForCustomer(Integer customerId, CarDto carDto) {
        if (customerId == null) {
            throw new BadRequestException("Customer id is required");
        }
        if (carDto == null) {
            throw new BadRequestException("car  is required");
        }

        Customer customer = customerRepository.findById(customerId).
                orElseThrow(() -> new NotFoundException("No customer found with id:" + customerId));

        Car retriveCar = customer.getCars().stream()
                .filter(car -> car.getCarId() != null && car.getCarId().equals(carDto.getCarId()))//It keeps only the elements where the condition is true
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Car with ID " + carDto.getCarId() + " does not belong to customer with ID " + customerId));

        Optional.ofNullable(carDto.getNumberPlate()).ifPresent(numberPlate -> retriveCar.setNumberPlate(numberPlate));
        Optional.ofNullable(carDto.getModel()).ifPresent(retriveCar::setModel);
        Optional.ofNullable(carDto.getMake()).ifPresent(retriveCar::setMake);
        Optional.ofNullable(carDto.getYear()).ifPresent(retriveCar::setYear);
        retriveCar.setCustomer(customer);
        carRepository.save(retriveCar);
        return carMapper.toCarDto(retriveCar);
    }

    public List<RepairOrderDto> findRepairOrdersByCustomerId(Integer customerId) {
        if (customerId == null) {
            throw new BadRequestException("Customer id is required");
        }
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("No Customer found with id:" + customerId));
        return Optional.ofNullable(customer.getRepairOrders())
                .orElse(Collections.emptyList())
                .stream()
                .map(repairOrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public RepairOrderDto addRepairOrderToCustomer(Integer customerId, RepairOrderDto repairOrderDto) {
        if (customerId == null) {
            throw new BadRequestException("Customer id is required");
        }
        if (repairOrderDto == null) {
            throw new BadRequestException("RepairOrderDto is required");
        }
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("No Customer found with id:" + customerId));
        RepairOrder repairOrder = repairOrderMapper.toEntityFromDto(repairOrderDto);
        repairOrder.setCustomer(customer);
        repairOrderRepository.save(repairOrder);
        customer.getRepairOrders().add(repairOrder);
        return repairOrderMapper.toDto(repairOrder);
    }

    //  1 RepairOrderDto updateRepairOrderForCustomer(Integer customerId, RepairOrderDto repairOrderDto)
    @Transactional
    public void removeRepairOrderFromCustomer(Integer customerId, Integer repairOrderId) {
        if (customerId == null) {
            throw new BadRequestException("Customer Id is required");
        }
        if (repairOrderId == null) {
            throw new BadRequestException("Repair order is Required");
        }
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("No Customer found with id:" + customerId));
        RepairOrder repairOrder = repairOrderRepository.findById(repairOrderId)
                .orElseThrow(() -> new NotFoundException("No repairOrder found with id" + repairOrderId));
        if (!customer.getRepairOrders().contains(repairOrder)) {
            throw new IllegalStateException("Repair order with id:" + repairOrderId + "does not belong to Customer with id:" + customerId);
        }
        customer.getRepairOrders().remove(repairOrder);
        logger.info("Repair order has been deleted with ID: {}", repairOrderId);
    }

    @Transactional
    public CustomerDtoWithCarList addCustomerWiًthCarList(CustomerDtoWithCarList customerDto) {
        if (customerDto == null) {
            throw new BadRequestException("CustomerRegister cannot be null");
        }
        Customer customerEntity = customerMapper.toEntityWithCarSet(customerDto);
        if (!CollectionUtils.isEmpty(customerDto.getCars())) {
            List<Car> cars = new ArrayList<>();
            for (CarDto carDto : customerDto.getCars()) {
                Car carEntity = carRepository.findById(carDto.getCarId())
                        .orElseGet(() -> carRepository.save(carMapper.toCarEntity(carDto)));
                carEntity.setCustomer(customerEntity);
                cars.add(carEntity);
            }
            customerEntity.setCars(cars);
        }
        customerRepository.save(customerEntity);
        return customerMapper.toCustomerDtoWithCarSet(customerEntity);
    }

    @Transactional
    public List<CarDto> updateCarsForCustomer(Integer customerId, List<CarDto> carDtoList) {
        if (customerId == null) {
            throw new BadRequestException("Customer Id is required");
        }
        if (carDtoList == null || carDtoList.isEmpty()) {
            throw new BadRequestException(" Car list is required");
        }
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("No Customer found with id:" + customerId));
        List<Integer> carEntityListIds = customer.getCars().stream()
                .map(Car::getCarId)
                .collect(Collectors.toList());


        List<Car> newCars = carDtoList.stream()
                .filter(carDto -> carDto.getCarId() != null && !carEntityListIds.contains(carDto.getCarId()))
                .map(carMapper::toCarEntity)
                .peek(car -> car.setCustomer(customer))
                .collect(Collectors.toList());
        if (!newCars.isEmpty()) {
            carRepository.saveAll(newCars);
        }


        for (Car car : customer.getCars()) {
            carDtoList.stream()
                    .filter(carDto -> carDto.getCarId() != null && car.getCarId().equals(carDto.getCarId()))
                    .findFirst()
                    .ifPresent(carDto -> {
                        carMapper.updateCarFromCarDto(carDto, car);
                        carRepository.save(car);
                    });
        }
        return findCarsByCustomerId(customerId);
    }
}


