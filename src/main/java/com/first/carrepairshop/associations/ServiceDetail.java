package com.first.carrepairshop.associations;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class ServiceDetail {
    private Integer serviceId;
    private String serviceName;
    private Integer servicePrice;
}
