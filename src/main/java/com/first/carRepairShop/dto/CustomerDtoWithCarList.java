package com.first.carRepairShop.dto;

import com.first.carRepairShop.entity.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDtoWithCarList {
    private Integer customerId;

    @NotNull(message = "Customer number cannot be null")
    @Pattern(regexp = "^CU\\d+$", message = "Customer number must start with 'CU' followed by digits only")
    private String customerNumber;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, message = "Name must have at least 2 characters")
    private String name;

    @NotBlank(message = "Lastname cannot be blank")
    @Size(min = 2, message = "Lastname must have at least 2 characters")
    private String lastname;

    @NotNull(message = "UserApp role is required")
    private Role role;

    @Valid
    private List<@NotNull(message = "Car cannot be null") CarDto> cars = new ArrayList<>();

    @Override
    public String toString() {
        return "CustomerDtoWithCarList{" +
                "customerId=" + customerId +
                ", customerNumber='" + customerNumber + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", cars=" + cars +
                '}';
    }
}
