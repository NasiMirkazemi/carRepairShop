package com.first.carrepairshop.controller;

import com.first.carrepairshop.dto.CustomerDto;
import com.first.carrepairshop.service.CustomerService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customerController")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.addCustomer(customerDto));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(customerService.getCustomer(id));
    }

    @PatchMapping("/update")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.updateCustomer(customerDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("customer whit id:" + id + "is deleted");
    }

}
