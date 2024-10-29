package com.first.carrepairshop.service;

import com.first.carrepairshop.dto.ServicesDto;
import com.first.carrepairshop.entity.Services;
import com.first.carrepairshop.repository.ServicesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicesService {
    private final ServicesRepository servicesRepository;

    public ServicesDto add(ServicesDto servicesDto) {
        Services services = servicesRepository.save(Services.builder()
                .serviceName(servicesDto.getServiceName())
                .description(servicesDto.getDescription())
                .price(servicesDto.getPrice())
                .durationInMinutes(servicesDto.getDurationInMinutes())
                .serviceStatus(servicesDto.getServiceStatus())
                .scheduledTime(servicesDto.getScheduledTime())
                .mechanics(servicesDto.getMechanics())
                .repairOrders(servicesDto.getRepairOrders())
                .build());
        servicesDto.setServiceId(services.getServiceId());
        return servicesDto;
    }

    public ServicesDto update(ServicesDto servicesDto) {
        Services services = servicesRepository.save(Services.builder()
                .serviceId(servicesDto.getServiceId())
                .serviceName(servicesDto.getServiceName())
                .description(servicesDto.getDescription())
                .price(servicesDto.getPrice())
                .durationInMinutes(servicesDto.getDurationInMinutes())
                .serviceStatus(servicesDto.getServiceStatus())
                .scheduledTime(servicesDto.getScheduledTime())
                .mechanics(servicesDto.getMechanics())
                .repairOrders(servicesDto.getRepairOrders())
                .build());
        return ServicesDto.builder()
                .serviceId(services.getServiceId())
                .serviceName(services.getServiceName())
                .description(services.getDescription())
                .price(services.getPrice())
                .durationInMinutes(services.getDurationInMinutes())
                .serviceStatus(services.getServiceStatus())
                .scheduledTime(services.getScheduledTime())
                .mechanics(services.getMechanics())
                .repairOrders(services.getRepairOrders())
                .build();

    }

    public void removeById(Integer id) {
        servicesRepository.deleteById(id);
        System.out.println("service " + id + "deleted");
    }

    public ServicesDto getById(Integer id) {
        Services services = servicesRepository.findById(id).get();
        return ServicesDto.builder()
                .serviceId(services.getServiceId())
                .serviceName(services.getServiceName())
                .description(services.getDescription())
                .price(services.getPrice())
                .durationInMinutes(services.getDurationInMinutes())
                .serviceStatus(services.getServiceStatus())
                .scheduledTime(services.getScheduledTime())
                .mechanics(services.getMechanics())
                .repairOrders(services.getRepairOrders())
                .build();
    }
}
