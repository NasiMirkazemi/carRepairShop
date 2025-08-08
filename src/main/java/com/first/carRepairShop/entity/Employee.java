package com.first.carRepairShop.entity;

import com.first.carRepairShop.dto.UserDto;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue("EMPLOYEE") // optional, only if you're using @DiscriminatorColumn
public class Employee extends UserApp {
    private Integer age;
    private LocalDate hireDate;
    private Integer salary;
    private String department;

}
