package com.first.carRepairShop.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServiceDetailDto {
    private Integer serviceId;
    private String serviceName;
    private BigDecimal servicePrice;

}
