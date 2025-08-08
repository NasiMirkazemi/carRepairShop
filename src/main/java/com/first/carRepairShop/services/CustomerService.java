package com.first.carRepairShop.services;

import com.first.carRepairShop.dto.CarDto;
import com.first.carRepairShop.dto.CustomerRegister;
import com.first.carRepairShop.dto.CustomerDtoWithCarList;
import com.first.carRepairShop.dto.RepairOrderDto;

import java.util.List;

public interface CustomerService {
    CustomerRegister addCustomer(CustomerRegister customerRegister);

    CustomerRegister updateCustomer(CustomerRegister customerRegister);

    void deleteCustomerById(Integer customerId);

    CustomerRegister getCustomerById(Integer customerId);

    List<CustomerRegister> getCustomerByNameAndLastname(String name, String lastname);

    CustomerRegister findCustomerByPhoneNumber(String phoneNumber);

    List<CustomerRegister> findAllCustomer();

    List<CarDto> findCarsByCustomerId(Integer customerId);

    CarDto addCarToCustomer(Integer customerId, CarDto carDto);

    void removeCarFromCustomer(Integer customerId, Integer carId);

    CarDto updateCarForCustomer(Integer customerId, CarDto carDto);

    List<RepairOrderDto> findRepairOrdersByCustomerId(Integer customerId);

    RepairOrderDto addRepairOrderToCustomer(Integer customerId, RepairOrderDto repairOrderDto);

    void removeRepairOrderFromCustomer(Integer customerId, Integer repairOrderId);

    CustomerDtoWithCarList addCustomerWiًthCarList(CustomerDtoWithCarList customerDto);

    List<CarDto> updateCarsForCustomer(Integer customerId, List<CarDto> carDtoList);

}
