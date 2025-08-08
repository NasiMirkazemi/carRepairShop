package com.first.carRepairShop.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RepairOrderDtoFull {

    private Integer repairOrderId;

    private String repairOrderNumber;

    @Size(min = 5, max = 255, message = "Description must be between 5 and 255 characters")
    private String description;

    private LocalDate createDate;

    @Valid
    @NotNull(message = "Customer details cannot be null")
    private CustomerRegister customer;

    @Valid
    @NotNull(message = "Car details cannot be null")
    private CarDto car;

    @NotEmpty(message = "Services list cannot be empty")
    @Valid
    private List<ServiceDetailDto> plannedServices = new ArrayList<>();

    @NotEmpty(message = "items list cannot be empty")
    @Valid
    private List<ItemDetailDto> plannedItems = new ArrayList<>();


}
