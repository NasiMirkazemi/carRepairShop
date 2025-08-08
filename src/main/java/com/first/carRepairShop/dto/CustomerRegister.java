package com.first.carRepairShop.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegister {
    private Integer id;
    private String customerNumber;


    @NotNull(message = "User data is required")
    @Valid
    private UserDto user;

    @Override
    public String toString() {
        return "CustomerRegister{" +
                "customerId=" + id +
                ", customerNumber='" + customerNumber + '\'' +
                ", user=" + user +
                '}';
    }
}
