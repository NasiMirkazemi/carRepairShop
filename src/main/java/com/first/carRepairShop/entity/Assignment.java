package com.first.carRepairShop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "repairOrder")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer assignmentId;

    @OneToOne
    @JoinColumn(name = "repair_order_id", nullable = false, unique = true)
    private RepairOrder repairOrder;
    private Integer mechanicId;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL)
    private List<WorkLog> workLogs = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private AssignmentStatus assignmentStatus;

    private LocalDate startDate;
    private LocalDate endDate;


}
