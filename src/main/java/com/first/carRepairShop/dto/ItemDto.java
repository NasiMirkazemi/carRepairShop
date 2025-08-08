package com.first.carRepairShop.dto;

import com.first.carRepairShop.entity.ItemQuality;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class ItemDto {
    private Integer itemId;

    @NotBlank(message = "Item name cannot be blank")
    private String name;

    @NotBlank(message = "Item type cannot be blank")
    private String type;

    @NotBlank(message = "Brand cannot be blank")
    private String brand;  // NEW FIELD: Brand of the item

    @NotNull(message = "Item Price cannot be null")
    private Integer itemPrice;


}
