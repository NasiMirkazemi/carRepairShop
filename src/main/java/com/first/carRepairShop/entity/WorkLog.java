package com.first.carRepairShop.entity;

import com.first.carRepairShop.associations.ItemDetail;
import com.first.carRepairShop.associations.ServiceDetail;
import com.first.carRepairShop.dto.ItemDetailDto;
import com.first.carRepairShop.dto.ItemDto;
import com.first.carRepairShop.dto.ServiceDetailDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer workLogId;

    @Column(unique = true, nullable = false, updatable = false)
    private String workLogNumber;

    private LocalDate createDate;

    @Enumerated(EnumType.STRING)
    private WorkLogStatus workLogStatus;

    @Enumerated(EnumType.STRING)
    private WorkLogSource workLogSource;


    @ManyToOne
    @JoinColumn(name = "mechanic_id", nullable = false)
    private Mechanic mechanic;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @ElementCollection
    @CollectionTable(name = "worK-log_service", joinColumns = @JoinColumn(name = "work-log-id"))
    private List<ServiceDetail> performedService = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "work-log-item", joinColumns = @JoinColumn(name = "work-log-id"))
    private List<ItemDetail> usedItem = new ArrayList<>();

    private LocalDateTime checkIn;  // Time when the mechanic clocked in
    private LocalDateTime checkOut;// Time when the mechanic clocked out
    private LocalDateTime pauseStart;
    private LocalDateTime pauseEnd;
    private LocalDateTime cancelTime;
    private Double totalHours;// Total hours worked (calculated)
    private String description;


}
