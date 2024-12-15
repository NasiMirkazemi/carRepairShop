package com.first.carrepairshop.service;

import com.first.carrepairshop.dto.ServicesDto;
import com.first.carrepairshop.entity.Services;
import com.first.carrepairshop.repository.ServicesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServicesService {
    private final ServicesRepository servicesRepository;

    public ServicesDto addService(ServicesDto servicesDto) {
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

    public ServicesDto getService(Integer id) {
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

    public ServicesDto updateService(ServicesDto servicesDto) {
        Optional<Services> optionalServices = servicesRepository.findById(servicesDto.getServiceId());
        Services serviceEntity = null;
        if (optionalServices.isPresent()) {
            serviceEntity = optionalServices.get();
            if (servicesDto.getServiceName() != null)
                serviceEntity.setServiceName(servicesDto.getServiceName());
            if (servicesDto.getDescription() != null)
                serviceEntity.setDescription(servicesDto.getDescription());
            if (servicesDto.getPrice() != null)
                serviceEntity.setPrice(servicesDto.getPrice());
            if (servicesDto.getDurationInMinutes() != null)
                serviceEntity.setDurationInMinutes(servicesDto.getDurationInMinutes());
            if (servicesDto.getScheduledTime() != null)
                serviceEntity.setScheduledTime(servicesDto.getScheduledTime());
            if (servicesDto.getServiceStatus() != null)
                serviceEntity.setServiceStatus(servicesDto.getServiceStatus());
            if (servicesDto.getMechanics() != null)
                serviceEntity.setMechanics(servicesDto.getMechanics());
            if (servicesDto.getRepairOrders() != null)
                serviceEntity.setRepairOrders(servicesDto.getRepairOrders());
            servicesRepository.save(serviceEntity);
        }
        return ServicesDto.builder()
                .serviceId(serviceEntity.getServiceId())
                .serviceName(serviceEntity.getServiceName())
                .description(serviceEntity.getDescription())
                .price(serviceEntity.getPrice())
                .durationInMinutes(serviceEntity.getDurationInMinutes())
                .serviceStatus(serviceEntity.getServiceStatus())
                .scheduledTime(serviceEntity.getScheduledTime())
                .mechanics(serviceEntity.getMechanics())
                .repairOrders(serviceEntity.getRepairOrders())
                .build();

    }

    public void deleteService(Integer id) {
        servicesRepository.deleteById(id);
        System.out.println("service " + id + "deleted");
    }


}
