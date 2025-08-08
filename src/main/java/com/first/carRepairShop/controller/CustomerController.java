package com.first.carRepairShop.controller;

import com.first.carRepairShop.dto.CarDto;
import com.first.carRepairShop.dto.CustomerRegister;
import com.first.carRepairShop.dto.CustomerDtoWithCarList;
import com.first.carRepairShop.dto.RepairOrderDto;
import com.first.carRepairShop.services.impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerServiceImpl customerServiceImpl;

    @PostMapping("/addCustomer")
    public ResponseEntity<CustomerRegister> addCustomer(@RequestBody CustomerRegister customerRegister) {
        CustomerRegister customerRegister1 = customerServiceImpl.addCustomer(customerRegister);
        return ResponseEntity.ok(customerRegister1);
    }

    @GetMapping("/get/{customerId}")
    public ResponseEntity<CustomerRegister> getCustomer(@PathVariable("customerId") Integer customerId) {

        CustomerRegister customerRegister = customerServiceImpl.getCustomerById(customerId);
        return ResponseEntity.ok(customerRegister);
    }

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable("customerId") Integer customerId) {
        customerServiceImpl.deleteCustomerById(customerId);
        return ResponseEntity.ok("customer has been deleted with id:" + customerId);
    }

    @PutMapping("/updateCustomer")
    public ResponseEntity<CustomerRegister> updateCustomer(@RequestBody CustomerRegister customerRegister) {
        CustomerRegister updatedCustomer = customerServiceImpl.updateCustomer(customerRegister);
        return ResponseEntity.ok(updatedCustomer);
    }

    @GetMapping("/getByNameAndLastname")
    public ResponseEntity<List<CustomerRegister>> getByNameAndLastName(@RequestParam String name, @RequestParam String lastname) {
        List<CustomerRegister> customerRegisterList = customerServiceImpl.getCustomerByNameAndLastname(name, lastname);
        return ResponseEntity.ok(customerRegisterList);

    }

    @GetMapping("/getByPhoneNumber")
    public ResponseEntity<CustomerRegister> getByPhoneNumber(@RequestParam String phoneNumber) {
        CustomerRegister customerRegister = customerServiceImpl.findCustomerByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(customerRegister);
    }

    @GetMapping("/getAllCustomer")
    public ResponseEntity<List<CustomerRegister>> getAllCustomer() {
        List<CustomerRegister> customerRegisterList = customerServiceImpl.findAllCustomer();
        return ResponseEntity.ok(customerRegisterList);
    }

    @GetMapping("/getCarsByCustomerId/{customerId}")
    public ResponseEntity<List<CarDto>> getCarsWithCustomerId(@PathVariable("customerId") Integer customerId) {
        List<CarDto> carDtoList = customerServiceImpl.findCarsByCustomerId(customerId);
        return ResponseEntity.ok(carDtoList);
    }

    @PostMapping("/addCarToCustomer/{customerId}/car")
    public ResponseEntity<CarDto> addCarToCustomer(@PathVariable("customerId") Integer customerId, @RequestBody CarDto carDto) {
        CarDto addedCardto = customerServiceImpl.addCarToCustomer(customerId, carDto);
        return ResponseEntity.ok(addedCardto);
    }

    @DeleteMapping("/removeCarFromCustomer/{customerId}/{carId}")

    public ResponseEntity<String> removeCarFromCustomer(@PathVariable("customerId") Integer customerId, @PathVariable("carId") Integer carId) {
        customerServiceImpl.removeCarFromCustomer(customerId, carId);
        return ResponseEntity.ok("car has been deleted with car id:" + carId + " from customer with id:" + customerId);
    }

    @PostMapping("/updateCarForCustomer/{customerId}/car")
    public ResponseEntity<CarDto> updateCarForCustomer(@PathVariable("customerId") Integer customerId, @RequestBody CarDto carDto) {
        CarDto updatedCar = customerServiceImpl.updateCarForCustomer(customerId, carDto);
        return ResponseEntity.ok(updatedCar);
    }

    @GetMapping("/findRepairOrdersByCustomerId")
    public ResponseEntity<List<RepairOrderDto>> findRepairOrdersByCustomerId(@PathVariable("customerId") Integer customerId) {
        List<RepairOrderDto> repairOrderDtoList = customerServiceImpl.findRepairOrdersByCustomerId(customerId);
        return ResponseEntity.ok(repairOrderDtoList);
    }

    @PostMapping("/addRepairOrderToCustomer/{customerId}")
    public ResponseEntity<RepairOrderDto> addRepairOrderToCustomer(@PathVariable("customerId") Integer customerId, @RequestBody RepairOrderDto repairOrderDto) {
        RepairOrderDto updatedRepairOrderDto = customerServiceImpl.addRepairOrderToCustomer(customerId, repairOrderDto);
        return ResponseEntity.ok(updatedRepairOrderDto);
    }

    @DeleteMapping("/removeRepairOrder/{customerId}/{repairOrderId}")
    public ResponseEntity<String> removeRepairOrderFromCustomer(@PathVariable("customerId") Integer customerId, @PathVariable("repairOrderId") Integer repairOrderId) {
        customerServiceImpl.removeRepairOrderFromCustomer(customerId, repairOrderId);
        return ResponseEntity.ok("Repair Order has been deleted with id:" + repairOrderId + "from customer with id:" + customerId);
    }

    @PostMapping("/addCustomerًWithCarList")
    public ResponseEntity<CustomerDtoWithCarList> addCustomerWithCarList(@RequestBody CustomerDtoWithCarList customerDto) {
        CustomerDtoWithCarList customerDtoWithCarList = customerServiceImpl.addCustomerWiًthCarList(customerDto);
        return ResponseEntity.ok(customerDtoWithCarList);
    }
    @PutMapping("/updateCars")

    public ResponseEntity<List<CarDto>> updateCarsForCustomer(@PathVariable("customerId") Integer customerId, @RequestBody List<CarDto> carDtoList) {
        List<CarDto> updatedCarDtoList = customerServiceImpl.updateCarsForCustomer(customerId, carDtoList);
        return ResponseEntity.ok(updatedCarDtoList);
    }


}
