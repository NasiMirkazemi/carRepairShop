package com.first.carrepairshop.service;

import com.first.carrepairshop.dto.CustomerDto;
import com.first.carrepairshop.entity.Customer;
import com.first.carrepairshop.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public CustomerDto getCustomer(Integer id) {
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

    public CustomerDto updateCustomer(CustomerDto customerDto) {
        Optional<Customer> customerOptional = customerRepository.findById(customerDto.getCustomerId());
        Customer customerEntity = null;
        if (customerOptional.isPresent()) {
            customerEntity = customerOptional.get();
            if (customerDto.getName() != null)
                customerEntity.setName(customerDto.getName());
            if (customerDto.getLastname() != null)
                customerEntity.setLastname(customerDto.getLastname());
            if (customerDto.getEmail() != null)
                customerEntity.setEmail(customerDto.getEmail());
            if (customerDto.getPhone() != null)
                customerEntity.setPhone(customerDto.getPhone());
            if (customerDto.getAddress() != null)
                customerEntity.setAddress(customerDto.getAddress());
            if (!customerDto.getInvoices().isEmpty()) {
                customerEntity.getInvoices().clear();
                customerEntity.getInvoices().addAll(customerDto.getInvoices());
            }
            if (!customerDto.getCar().isEmpty()) {
                customerEntity.getCar().clear();
                customerEntity.getCar().addAll(customerDto.getCar());
            }
            customerRepository.save(customerEntity);
        }
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

    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }

}

