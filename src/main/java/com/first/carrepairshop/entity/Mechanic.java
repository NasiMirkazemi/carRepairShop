
package com.first.carrepairshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

public class Mechanic extends Employee {

    private String specialty;
    private String certificate;
    private Integer hourlyRate;

    @ManyToMany
    @JoinTable(name = "mechanic_services", joinColumns=@JoinColumn(name="mechanicId"),
    inverseJoinColumns = @JoinColumn(name="serviceId"))

    private Set<Services> services=new HashSet<>();

}



