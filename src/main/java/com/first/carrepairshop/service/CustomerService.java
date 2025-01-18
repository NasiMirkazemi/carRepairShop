package com.first.carrepairshop.service;

import com.first.carrepairshop.dto.CarDto;
import com.first.carrepairshop.dto.CustomerDto;
import com.first.carrepairshop.dto.InvoiceDto;
import com.first.carrepairshop.entity.Car;
import com.first.carrepairshop.entity.Customer;
import com.first.carrepairshop.entity.Invoice;
import com.first.carrepairshop.exception.IllegalException;
import com.first.carrepairshop.exception.NotfoundException;
import com.first.carrepairshop.mapper.CarMapper;
import com.first.carrepairshop.mapper.CustomerMapper;
import com.first.carrepairshop.mapper.InvoiceMapper;
import com.first.carrepairshop.repository.CarRepository;
import com.first.carrepairshop.repository.CustomerRepository;
import com.first.carrepairshop.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;
    private final InvoiceRepository invoiceRepository;
    private final CustomerMapper customerMapper;
    private final CarMapper carMapper;
    private final InvoiceMapper invoiceMapper;

    public CustomerDto addCustomer(CustomerDto customerDto) {
        if (customerDto == null) {
            throw new IllegalException("CustomerDto cannot be null");
        }
        Customer customerEntity = customerMapper.toCustomerEntity(customerDto);
        if (customerDto.getCarsDto() != null && !customerDto.getCarsDto().isEmpty()) {
            List<Car> cars = new ArrayList<>();
            for (CarDto carDto : customerDto.getCarsDto()) {
                Car carEntity = carRepository.findById(carDto.getCarId()).orElseGet(() -> carRepository.save(carMapper.toCarEntity(carDto)));
                carEntity.setCustomer(customerEntity);
                cars.add(carEntity);
            }
            customerEntity.setCars(cars);
        }
        customerRepository.save(customerEntity);
        return customerMapper.toCustomerDto(customerEntity);
    }


    public CustomerDto getCustomer(Integer id) {
        Customer customerEntity = customerRepository.findById(id).orElseThrow(() -> new NotfoundException("Customer whit id :" + id + "not found"));
        return customerMapper.toCustomerDto(customerEntity);
    }

    public CustomerDto updateCustomer(CustomerDto customerDto) {
        Customer customerEntity = customerRepository.findById(customerDto.getCustomerId())
                .orElseThrow(() -> new NotfoundException("Customer not found whit Id" + customerDto.getCustomerId()));
        //(condition) ? (value if ture) : (value if false) which value set based on a condition
        customerEntity.setName(customerDto.getName() != null ? customerDto.getName() : customerEntity.getName());
        customerEntity.setLastname(customerDto.getLastname() != null ? customerDto.getLastname() : customerEntity.getLastname());
        customerEntity.setEmail(customerDto.getEmail() != null ? customerDto.getEmail() : customerEntity.getEmail());
        customerEntity.setPhone(customerDto.getPhone() != null ? customerDto.getPhone() : customerEntity.getPhone());
        customerEntity.setAddress(customerDto.getAddress() != null ? customerDto.getAddress() : customerEntity.getAddress());
        //invoice List in Customer fields
        if (customerDto.getInvoicesDto() != null && !customerDto.getInvoicesDto().isEmpty()) {
            List<Invoice> invoiceList = new ArrayList<>();
            for (InvoiceDto invoiceDto : customerDto.getInvoicesDto()) {
                Invoice invoiceEntity = invoiceRepository.findById(invoiceDto.getInvoiceId())
                        .orElseThrow(() -> new NotfoundException("Invoice not found whit id: " + invoiceDto.getInvoiceId()));
                invoiceEntity.setCustomer(customerEntity);
                invoiceList.add(invoiceEntity);
            }
            customerEntity.setInvoices(invoiceList);
        }
        //cars list in Customer fields
        if (customerDto.getCarsDto() != null && !customerDto.getCarsDto().isEmpty()) {
            List<Car> carList = new ArrayList<>();
            for (CarDto carDto : customerDto.getCarsDto()) {
                Car carEntity = carRepository.findById(carDto.getCarId())
                        .orElseThrow(() -> new NotfoundException("Car not found whit id: " + carDto.getCarId()));
                carEntity.setCustomer(customerEntity);
                carList.add(carEntity);
            }
            customerEntity.getCars().addAll(carList);

        }
        customerRepository.save(customerEntity);
        return customerMapper.toCustomerDto(customerEntity);
    }

    public CustomerDto updateCustomer1(CustomerDto customerDto) {
        Customer customerEntity = customerRepository.findById(customerDto.getCustomerId())
                .orElseThrow(() -> new NotfoundException("Customer not found whit id:" + customerDto.getCustomerId()));
        customerEntity.setName(Optional.ofNullable(customerDto.getName()).orElse(customerEntity.getName()));
        customerEntity.setLastname(Optional.ofNullable(customerDto.getLastname()).orElse(customerEntity.getLastname()));
        customerEntity.setEmail(Optional.ofNullable(customerDto.getEmail()).orElse(customerEntity.getEmail()));
        customerEntity.setPhone(Optional.ofNullable(customerDto.getPhone()).orElse(customerEntity.getPhone()));
        customerEntity.setAddress(Optional.ofNullable(customerDto.getAddress()).orElse(customerEntity.getAddress()));
        //Mange cars list update
        if (customerDto.getCarsDto() != null && !customerDto.getCarsDto().isEmpty()) {//Get the  list of car  Ids
            List<Car> carEntityList = customerEntity.getCars();
            //exist carIds in database
            List<Integer> carEntityListIds = carEntityList.stream().map(Car::getCarId).toList();
            //cars that are not in customerDto updated list
            List<Car> carsToRemove = carEntityList.stream()
                    .filter(car -> customerDto.getCarsDto().stream().noneMatch(carDto -> carDto.getCarId().equals(car.getCarId()))).toList();
            for (Car car : carsToRemove) {
                car.setCustomer(null);//unlinked the car from the customer
                carRepository.save(car);
            }
            customerEntity.getCars().removeAll(carsToRemove);
            //map the updated list of CarDto to objects to Car Entity
            List<Car> carsToAddOrUpdate = customerDto.getCarsDto().stream()
                    .map(carMapper::toCarEntity)//mapping each carDto into a car entity
                    .peek(car -> car.setCustomer(customerEntity))//this method adding side effect to processing of stream and sets the customer reference for each car
                    .toList();
            for (Car car : carsToAddOrUpdate) {//iterate over each car in list
                if (!carEntityListIds.contains(car.getCarId())) { //check if car is new,this determines whether the car is new and needs tobe added in customer's car list
                    customerEntity.getCars().add(car);
                }
                carRepository.save(car);
            }
        }
        if (customerDto.getInvoicesDto() != null) {
            List<Invoice> invoiceEntityList = customerEntity.getInvoices();
            List<Integer> invoiceEntityListIds = invoiceEntityList.stream().map(Invoice::getInvoiceId).toList();
            List<Invoice> invoiceToRemove = invoiceEntityList.stream()
                    .filter(invoice -> customerDto.getInvoicesDto().stream().noneMatch(invoiceDto -> invoiceDto.getInvoiceId().equals(invoice.getInvoiceId())))
                    .toList();
            customerEntity.getInvoices().removeAll(invoiceToRemove);
            for (Invoice invoice : invoiceToRemove) {
                invoice.setCustomer(null);
                invoiceRepository.save(invoice);
            }
            List<Invoice> invoicesToAddOrUpdate = customerDto.getInvoicesDto().stream()
                    .map(invoiceMapper::toInvoiceEntity)
                    .peek(invoice -> invoice.setCustomer(customerEntity))
                    .toList();
            for (Invoice invoice : invoicesToAddOrUpdate) {
                if (!invoiceEntityListIds.contains(invoice.getInvoiceId())) {
                    customerEntity.getInvoices().add(invoice);
                }
                invoiceRepository.save(invoice);
            }
        }
        customerRepository.save(customerEntity);
        return customerMapper.toCustomerDto(customerEntity);

    }

    public void deleteCustomer(Integer id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new NotfoundException("customer not found whit id:" + id));
        customerRepository.delete(customer);
    }

    public CarDto addCarToCustomer(Integer customerId, CarDto carDto) {
        Customer customerEntity = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotfoundException("Customer not found whit id: " + customerId));
        Car retriveCar = carRepository.findByNumberPlate(carDto.getNumberPlate()).orElseGet(() -> {
            Car carEntity = carMapper.toCarEntity(carDto);
            carEntity.setCustomer(customerEntity);
            return carRepository.save(carEntity);
        });
        if (!customerEntity.getCars().contains(retriveCar)) {
            retriveCar.setCustomer(customerEntity);
            customerEntity.getCars().add(retriveCar);
            customerRepository.save(customerEntity);
        }
        return carMapper.toCarDto(retriveCar);
    }

}

