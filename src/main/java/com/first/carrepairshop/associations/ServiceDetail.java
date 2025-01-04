package com.first.carrepairshop.associations;

import jakarta.persistence.Embeddable;
import lombok.Data;
import org.springframework.stereotype.Component;

@Embeddable
@Data

public class ServiceDetail {
    private Integer serviceId;
    private String serviceName;
    private Integer servicePrice;

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(Integer servicePrice) {
        this.servicePrice = servicePrice;
    }
}
