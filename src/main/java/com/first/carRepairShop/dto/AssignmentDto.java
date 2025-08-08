package com.first.carRepairShop.dto;

import com.first.carRepairShop.entity.AssignmentStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignmentDto {
    private Integer assignmentId;

    @NotNull(message = "Repair order cannot be null")
    private Integer repairOrderId;
    @NotNull(message = "Mechanic Id con not be null")
    private Integer mechanicId;
    @Valid
    private List<WorkLogDto> workLogs = new ArrayList<>();

    private String assignmentStatus;

    private LocalDate startDate;
    private LocalDate endDate;


}
