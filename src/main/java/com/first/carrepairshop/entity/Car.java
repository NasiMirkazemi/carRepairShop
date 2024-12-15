package com.first.carrepairshop.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;
    private Long numberPlate;
    private String make;
    private String model;
    private Integer year;


   @ManyToOne(optional = true)
    @JoinColumn(name = "customerId",nullable = true)//refers to the "car" field in Customer
    private Customer customer;
}
