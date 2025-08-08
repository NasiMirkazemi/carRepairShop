package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.dto.ServiceDto;
import com.first.carRepairShop.entity.Services;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.mapper.MechanicMapper;
import com.first.carRepairShop.mapper.ServiceMapper;
import com.first.carRepairShop.repository.ServicesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicesService {
    private final ServicesRepository servicesRepository;
    private final ServiceMapper serviceMapper;
    private final MechanicMapper mechanicMapper;


    public ServiceDto addService(ServiceDto servicesDto) {
        Services services = serviceMapper.toEntity(servicesDto);
        Services savedService = servicesRepository.save(services);
        return serviceMapper.toDto(savedService);
    }

    public ServiceDto getService(Integer serviceId) {
        if (serviceId == null)
            throw new BadRequestException("Service Id is required");
        Services services = servicesRepository.findById(serviceId)
                .orElseThrow(() -> new NotFoundException("No Service found with id :" + serviceId));
        return serviceMapper.toDto(services);
    }

    public ServiceDto updateService(ServiceDto servicesDto) {
        if (servicesDto == null)
            throw new BadRequestException("New Info for service is required");
        Services services = servicesRepository.findById(servicesDto.getServiceId())
                .orElseThrow(() -> new NotFoundException("No Service found with id :" + servicesDto.getServiceId()));
        Optional.ofNullable(servicesDto.getServiceName()).ifPresent(services::setServiceName);
        Optional.ofNullable(servicesDto.getServicePrice()).ifPresent(services::setServicePrice);
        Optional.ofNullable(servicesDto.getDescription()).ifPresent(services::setDescription);
        Services savedService = servicesRepository.save(services);
        return serviceMapper.toDto(savedService);
    }

    public void deleteService(Integer serviceId) {
        Services services = servicesRepository.findById(serviceId)
                .orElseThrow(() -> new NotFoundException("No Service found with id :" + serviceId));
        servicesRepository.delete(services);
        log.info("Service has been deleted with id :{} ", serviceId);
    }


}
