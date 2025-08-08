package com.first.carRepairShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appointmentId;

    @Column(unique = true, nullable = false)
    private String appointmentNumber;
    private Integer customerId;
    private Integer carId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;
    private String notes;


}

