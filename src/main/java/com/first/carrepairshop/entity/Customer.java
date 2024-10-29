package com.first.carrepairshop.entity;

import com.first.carrepairshop.entity.Car;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;
    private String name;
    private String lastname;
    private String email;
    private String phone;
    private String address;
   @OneToMany(mappedBy = "customer")
    private   List<Invoice> invoices;

    @OneToMany( mappedBy = "customer")
    protected List<Car>  car;

}
