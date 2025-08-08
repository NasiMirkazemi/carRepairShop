package com.first.carRepairShop.mapper;

import com.first.carRepairShop.dto.CustomerRegister;
import com.first.carRepairShop.dto.CustomerDtoFull;
import com.first.carRepairShop.dto.CustomerDtoWithCarList;
import com.first.carRepairShop.dto.CustomerDtoWithRepairOrderList;
import com.first.carRepairShop.entity.Customer;
import com.first.carRepairShop.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CustomerMapper {


    @Mappings({
            @Mapping(target = "id", source = "id"), // entity.id → dto.id
            @Mapping(target = "customerNumber", source = "customerNumber"),
            // basic fields
            @Mapping(target = "user.username", source = "username"),
            @Mapping(target = "user.password", source = "password"),
            @Mapping(target = "user.name", source = "name"),
            @Mapping(target = "user.lastname", source = "lastname"),
            @Mapping(target = "user.email", source = "email"),
            @Mapping(target = "user.phone", source = "phone"),
            @Mapping(target = "user.address", source = "address"),
            @Mapping(target = "user.role", source = "role"),
            // here set user.id explicitly from entity.id
            @Mapping(target = "user.id", source = "id")
    })
    CustomerRegister toDto(Customer customer);

    @Mappings({
            @Mapping(target = "id", source = "id"), // dto.id → entity.id
            @Mapping(target = "username", source = "user.username"),
            @Mapping(target = "password", source = "user.password"),
            @Mapping(target = "name", source = "user.name"),
            @Mapping(target = "lastname", source = "user.lastname"),
            @Mapping(target = "email", source = "user.email"),
            @Mapping(target = "phone", source = "user.phone"),
            @Mapping(target = "address", source = "user.address"),
            @Mapping(target = "role", source = "user.role"),
            @Mapping(target = "customerNumber", source = "customerNumber")
    })
    Customer toEntity(CustomerRegister customerRegister);

    @Mapping(target = "cars", source = "cars")
    CustomerDtoWithCarList toCustomerDtoWithCarSet(Customer customer);

    @Mapping(target = "cars", source = "cars")
    Customer toEntityWithCarSet(CustomerDtoWithCarList customerDtoWithCarList);

    @Mapping(target = "repairOrders", source = "repairOrders")
    CustomerDtoWithRepairOrderList toCustomerDtoWithRepairOrderList(Customer customer);

    @Mapping(target = "repairOrders", source = "repairOrders")
    Customer toEntityWithRepairOrderList(CustomerDtoWithRepairOrderList customerDtoWithRepairOrderList);

    @Mapping(target = "cars", source = "cars")
    @Mapping(target = "repairOrders", source = "repairOrders")
    CustomerDtoFull toCustomerDtoFull(Customer customer);

    @Mapping(target = "cars", source = "cars")
    @Mapping(target = "repairOrders", source = "repairOrders")
    Customer toEntityWithCustomerDtoFull(CustomerDtoFull customerDtoFull);

    List<CustomerRegister> toCustomerDtoList(List<Customer> customerList);

    List<Customer> toCustomerEntityList(List<CustomerRegister> customerRegisterList);


}
