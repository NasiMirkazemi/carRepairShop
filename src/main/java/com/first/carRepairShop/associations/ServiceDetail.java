package com.first.carRepairShop.associations;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ServiceDetail {
    private Integer serviceId; //refer to service that serviceDetail is associated with
    private String serviceName;
    private BigDecimal servicePrice;


}
