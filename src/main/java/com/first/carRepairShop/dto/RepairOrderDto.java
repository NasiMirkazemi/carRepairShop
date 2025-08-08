package com.first.carRepairShop.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.first.carRepairShop.entity.RepairStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RepairOrderDto {
    private Integer repairOrderId;

    private String repairOrderNumber;

    @Size(min = 5, max = 255, message = "Description must be between 5 and 255 characters")
    private String description;

    private LocalDate createDate;

    @NotNull(message = "Customer id cannot be null")
    private Integer customerId;

    @NotNull(message = "Car id cannot be null")
    private Integer carId;

    @NotEmpty(message = "Services list cannot be empty")
    @Valid
    private List<ServiceDetailDto> plannedServices = new ArrayList<>();

    @NotEmpty(message = "items list cannot be empty")
    @Valid
    private List<ItemDetailDto> plannedItems = new ArrayList<>();
    private String repairStatus;
    private LocalDateTime statusLastUpdated;

}
