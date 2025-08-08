package com.first.carRepairShop.dto;

import com.first.carRepairShop.entity.AssignmentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
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
public class AssignmentDtoFull {
    private Integer assignmentId;

    @NotNull(message = "Repair order cannot be null")
    private RepairOrderDto repairOrderDto;

    @NotNull(message = "Work logs cannot be null")
    @Size(min = 1, message = "At least one work log is required")
    private List<WorkLogDto> workLogs = new ArrayList<>();

    @NotNull(message = "Status cannot be null")
    private AssignmentStatus status;

    @NotNull(message = "Start date cannot be null")
    @PastOrPresent(message = "Start date cannot be in the future")
    private LocalDate startDate;
    private LocalDate endDate;


}
