package com.first.carrepairshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serviceId;
    private String serviceName;
    private String description;
    private Integer price;
    private Duration durationInMinutes;
    private LocalDate scheduledTime;

    @Enumerated(EnumType.STRING)
    private ServiceStatus serviceStatus;//no fix, fix,under fixing

    @OneToMany(mappedBy = "services")
    private  List<Mechanic> mechanics;

    @ManyToMany(mappedBy = "services")
    private Set<RepairOrder> repairOrders=new HashSet<>();
}
