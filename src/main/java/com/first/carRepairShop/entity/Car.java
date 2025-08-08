package com.first.carRepairShop.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;
    @Column(unique = true, nullable = false)
    private String numberPlate;
    private String make;
    private String model;
    private Integer year;

    @ManyToOne(optional = true)
    @JoinColumn(name = "customer_id",nullable = true)//refers to the "cars" field in Customer
    private Customer customer;
    private LocalDate createAt;


    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", numberPlate='" + numberPlate + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", customer=" + customer +
                ", createAt=" + createAt +
                '}';
    }
}

