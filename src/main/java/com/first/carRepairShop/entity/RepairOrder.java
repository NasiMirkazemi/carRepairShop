package com.first.carRepairShop.entity;

import com.first.carRepairShop.associations.ItemDetail;
import com.first.carRepairShop.associations.ServiceDetail;
import com.first.carRepairShop.dto.ItemDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "assignment")
public class RepairOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer repairOrderId;

    private String repairOrderNumber;
    private String description;
    private LocalDate createDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer; // ✅ Each repair orders is associated with ONE customer.

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car; // ✅ Directly links to a specific car


    @OneToOne(mappedBy = "repairOrder", cascade = CascadeType.ALL)
    private Assignment assignment;

    @ElementCollection
    @CollectionTable(name = "repair-order-item", joinColumns = @JoinColumn(name = "repair-order-id"))
    private List<ItemDetail> plannedItems= new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "repair-order-service", joinColumns = @JoinColumn(name = "repair-order-id"))
    private List<ServiceDetail> plannedServices = new ArrayList<>();
    //  Keep List<Item>
    @Enumerated(EnumType.STRING)
    private RepairStatus repairStatus;
    private LocalDateTime statusLastUpdated;

}
