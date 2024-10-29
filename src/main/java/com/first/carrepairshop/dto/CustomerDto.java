package com.first.carrepairshop.dto;

import com.first.carrepairshop.entity.Car;
import com.first.carrepairshop.entity.Invoice;
import com.first.carrepairshop.entity.RepairOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CustomerDto {
    private List<Car> car;
    private Integer customerId;
    private String name;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    private List<Invoice> invoices;

}
