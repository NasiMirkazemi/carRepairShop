
package com.first.carrepairshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;




@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Mechanic extends Employee {

    private String specialty;
    private String certificate;
    private Integer hourlyRate;

    @ManyToOne
    @JoinColumn(name = "serviceId")
    private Services services;
}


