package com.first.carRepairShop.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("MECHANIC")
public class Mechanic extends Employee {

    private String specialty;
    private String certificate;
    private BigDecimal hourlyRate;
    @OneToMany(mappedBy = "mechanic", cascade = CascadeType.ALL)
    private List<WorkLog> workLogs = new ArrayList<>(); // Links to WorkLog for tracking hours and assignments

    @Override
    public String toString() {
        return "Mechanic{" +
                "specialty='" + specialty + '\'' +
                ", certificate='" + certificate + '\'' +
                ", hourlyRate=" + hourlyRate +
                '}';
    }
}



