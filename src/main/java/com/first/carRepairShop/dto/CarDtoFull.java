package com.first.carRepairShop.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDtoFull {

        private Integer carId;

        @NotNull(message = "Number plate cannot be null")
        private String numberPlate;

        @NotNull(message = "Make cannot be null")
        @Size(min = 2, message = "Make must have at least 2 characters")
        private String make;

        @NotNull(message = "Model cannot be null")
        @Size(min = 2, message = "Model must have at least 2 characters")
        private String model;

        @NotNull(message = "Year cannot be null")
        private Integer year;

        @NotNull(message = "Customer cannot be null")
        private CustomerRegister customer;

        @Override
        public String toString() {
                return "CarDtoFull{" +
                        "carId=" + carId +
                        ", numberPlate='" + numberPlate + '\'' +
                        ", make='" + make + '\'' +
                        ", model='" + model + '\'' +
                        ", year=" + year +
                        ", customer=" + customer +
                        '}';
        }
}

