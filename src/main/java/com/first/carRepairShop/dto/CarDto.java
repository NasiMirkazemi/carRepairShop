package com.first.carRepairShop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CarDto {
    private Integer carId;
    @NotBlank(message = "numberPlate cannot be null")
    private String numberPlate;
    @NotNull(message = "numberPlate cannot be null")
    @Size(min = 2, message = "Make must have at least 2 characters")
    private String make;
    @NotNull(message = "model cannot be null")
    @Size(min = 2, message = "Model must have at least 2 characters")
    private String model;
    @NotNull(message = "year cannot be null")
    private Integer year;
    @NotNull(message="Customer ID cannot be null")
    @Positive(message = "Customer ID must be a positive number")
    private Integer customerId;
    private LocalDate createAt;

    @Override
    public String toString() {
        return "CarDto{" +
                "carId=" + carId +
                ", numberPlate='" + numberPlate + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", customerId=" + customerId +
                ", createAt=" + createAt +
                '}';
    }
}
