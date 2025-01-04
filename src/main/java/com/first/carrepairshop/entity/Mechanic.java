package com.first.carrepairshop.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Mechanic extends Employee {

    private String specialty;
    private String certificate;
    private Integer hourlyRate;

    @ManyToMany
    @JoinTable(name = "mechanic_service",
            joinColumns = @JoinColumn(name = "mechanic_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private List<Services> services = new ArrayList<>();

}



