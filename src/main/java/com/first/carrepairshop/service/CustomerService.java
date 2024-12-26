package com.first.carrepairshop.service;

import com.first.carrepairshop.dto.CarDto;
import com.first.carrepairshop.dto.CustomerDto;
import com.first.carrepairshop.dto.InvoiceDto;
import com.first.carrepairshop.entity.Car;
import com.first.carrepairshop.entity.Customer;
import com.first.carrepairshop.entity.Invoice;
import com.first.carrepairshop.exception.CarNotfoundException;
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
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
        Customer customerEntity = Customer.builder()
                .name(customerDto.getName())
                .lastname(customerDto.getLastname())
                .email(customerDto.getEmail())
                .phone(customerDto.getPhone())
                .address(customerDto.getAddress())
                .build();
        if (customerDto.getCars() != null && !customerDto.getCars().isEmpty()) {
            List<Car> cars = new ArrayList<>();
            for (CarDto carDto : customerDto.getCars()) {
                Car carEntity = carRepository.findById(carDto.getCarId()).orElse(null);
                if (carEntity != null) {
                    carEntity.setCustomer(customerEntity);
                    cars.add(carEntity);
                } else {
                    throw new CarNotfoundException("cars whit " + carDto.getCarId() + " not found");
                }
                customerEntity.setCars(cars);
            }
            customerRepository.save(customerEntity);
            if (customerEntity.getCars() != null) {
                for (Car car : customerEntity.getCars()) {
                    carRepository.save(car);
                }
            }
        }
        return customerMapper.toCustomerDto(customerEntity);
    }


    public CustomerDto getCustomer(Integer id) {
        Customer customerEntity = customerRepository.findById(id).get();
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
        if ( customerDto.getInvoiceDtos()!=null&&!customerDto.getInvoiceDtos().isEmpty()) {
            List<Invoice> invoiceList = new ArrayList<>();
            for (InvoiceDto invoiceDto : customerDto.getInvoiceDtos()) {
                Invoice invoiceEntity = invoiceRepository.findById(invoiceDto.getInvoiceId())
                        .orElseThrow(() -> new NotfoundException("Invoice not found whit id: " + invoiceDto.getInvoiceId()));
                invoiceEntity.setCustomer(customerEntity);
                invoiceList.add(invoiceEntity);
            }
            customerEntity.setInvoices(invoiceList);
        }
        //cars list in Customer fields
        if ( customerDto.getCars()!=null&&!customerDto.getCars().isEmpty()) {
            List<Car> carList = new ArrayList<>();
            for (CarDto carDto : customerDto.getCars()) {
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
        if (customerDto.getCars() != null) {//Get the  list of car  Ids
            List<Car> carEntityList = customerEntity.getCars();
            //exist carIds in database
            List<Integer> carEntityListIds = carEntityList.stream().map(Car::getCarId).toList();
            //cars that are not in customerDto updated list
            List<Car> carsToRemove = carEntityList.stream()
                    .filter(car -> customerDto.getCars().stream().noneMatch(carDto -> carDto.getCarId().equals(car.getCarId()))).toList();
            customerEntity.getCars().removeAll(carsToRemove);
            for (Car car : carsToRemove) {
                car.setCustomer(null);//unlinked the car from the customer
                carRepository.save(car);
            }//map the updated list of CarDto to objects to Car Entity
            List<Car> carsToAddOrUpdate = customerDto.getCars().stream()
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
        if (customerDto.getInvoiceDtos() != null) {
            List<Invoice> invoiceEntityList = customerEntity.getInvoices();
            List<Integer> invoiceEntityListIds = invoiceEntityList.stream().map(Invoice::getInvoiceId).toList();
            List<Invoice> invoiceToRemove = invoiceEntityList.stream()
                    .filter(invoice -> customerDto.getInvoiceDtos().stream().noneMatch(invoiceDto -> invoiceDto.getInvoiceId().equals(invoice.getInvoiceId())))
                    .toList();
            customerEntity.getInvoices().removeAll(invoiceToRemove);
            for (Invoice invoice : invoiceToRemove) {
                invoice.setCustomer(null);
                invoiceRepository.save(invoice);
            }
            List<Invoice> invoicesToAddOrUpdate = customerDto.getInvoiceDtos().stream()
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
        return customerMapper.toCustomerDto(customerEntity);

    }


    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }

}

