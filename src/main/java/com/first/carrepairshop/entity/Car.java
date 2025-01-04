package com.first.carrepairshop.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;
    private Long numberPlate;
    private String make;
    private String model;
    private Integer year;

    @ManyToOne(optional = true)
    @JoinColumn(name = "customer_id", nullable = true)//refers to the "cars" field in Customer
    private Customer customer;


}
