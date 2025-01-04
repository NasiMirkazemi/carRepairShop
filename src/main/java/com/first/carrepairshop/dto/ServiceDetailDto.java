package com.first.carrepairshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ServiceDetailDto {
    private Integer serviceId;
    private String serviceName;
    private Integer servicePrice;

}
