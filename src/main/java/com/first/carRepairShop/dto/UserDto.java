package com.first.carRepairShop.dto;

import com.first.carRepairShop.annotations.ValidRoleName;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Integer id;
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
            message = "Password must be at least 8 characters long," +
                    " contain one uppercase letter" +
                    ", one lowercase letter, one number, and one special character"
    )
    @Size(min = 8)
    private String password;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "lastname is required")
    private String lastname;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    private String phone;

    @NotBlank(message = "Address cannot be blank")
    private String address;
    @ValidRoleName
    private RolesDto role;
}
