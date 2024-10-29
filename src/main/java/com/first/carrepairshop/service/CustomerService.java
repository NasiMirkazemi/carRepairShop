package com.first.carrepairshop.service;

import com.first.carrepairshop.dto.CustomerDto;
import com.first.carrepairshop.entity.Customer;
import com.first.carrepairshop.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerDto addCustomer(CustomerDto customerDto) {
        Customer customerEntity = customerRepository.save(Customer.builder()
                .name(customerDto.getName())
                .lastname(customerDto.getLastname())
                .email(customerDto.getEmail())
                .phone(customerDto.getPhone())
                .address(customerDto.getAddress())
                .car(customerDto.getCar())
                .invoices(customerDto.getInvoices())
                .build());
        customerDto.setCustomerId(customerEntity.getCustomerId());
        return customerDto;
    }

    public CustomerDto getById(Integer id) {
        Customer customerEntity = customerRepository.findById(id).get();
        return CustomerDto.builder()
                .customerId(customerEntity.getCustomerId())
                .name(customerEntity.getName())
                .lastname(customerEntity.getLastname())
                .email(customerEntity.getEmail())
                .phone(customerEntity.getPhone())
                .address(customerEntity.getAddress())
                .car(customerEntity.getCar())
                .invoices(customerEntity.getInvoices())
                .build();
    }

    public void removeCustomerById(Integer id) {
        customerRepository.deleteById(id);
        System.out.println("user " + id + "deleted");
    }

    public CustomerDto update(CustomerDto customerDto) {
        Customer customerEntity = customerRepository.save(Customer.builder()
                .customerId(customerDto.getCustomerId())
                .name(customerDto.getName())
                .lastname(customerDto.getLastname())
                .email(customerDto.getEmail())
                .phone(customerDto.getPhone())
                .address(customerDto.getAddress())
                .car(customerDto.getCar())
                .invoices(customerDto.getInvoices())
                .build());
        return CustomerDto.builder()
                .customerId(customerEntity.getCustomerId())
                .name(customerEntity.getName())
                .lastname(customerEntity.getLastname())
                .email(customerEntity.getEmail())
                .phone(customerEntity.getPhone())
                .address(customerEntity.getAddress())
                .car(customerEntity.getCar())
                .invoices(customerEntity.getInvoices())
                .build();
    }
}

