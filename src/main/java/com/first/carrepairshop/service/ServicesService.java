package com.first.carrepairshop.service;

import com.first.carrepairshop.dto.ServicesDto;
import com.first.carrepairshop.entity.Services;
import com.first.carrepairshop.exception.NotfoundException;
import com.first.carrepairshop.mapper.MechanicMapper;
import com.first.carrepairshop.mapper.ServiceMapper;
import com.first.carrepairshop.repository.ServicesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.first.carrepairshop.entity.Services.*;

@Service
public class ServicesService {
    private final ServicesRepository servicesRepository;
    private final ServiceMapper serviceMapper;
    private final MechanicMapper mechanicMapper;

    public ServicesService(ServicesRepository servicesRepository, ServiceMapper serviceMapper, MechanicMapper mechanicMapper) {
        this.servicesRepository = servicesRepository;
        this.serviceMapper = serviceMapper;
        this.mechanicMapper = mechanicMapper;
    }

    public ServicesDto addService(ServicesDto servicesDto) {
        Services service = servicesRepository.save(serviceMapper.toServiceEntity(servicesDto));
        servicesDto.setServiceId(service.getServiceId());
        return servicesDto;
    }

    public ServicesDto getService(Integer id) {
        Services services = servicesRepository.findById(id).get();
        return serviceMapper.toServicesDto(services);
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
            if (servicesDto.getServicePrice() != null)
                serviceEntity.setServicePrice(servicesDto.getServicePrice());
            if (servicesDto.getDurationInMinutes() != null)
                serviceEntity.setDurationInMinutes(servicesDto.getDurationInMinutes());
            if (servicesDto.getScheduledTime() != null)
                serviceEntity.setScheduledTime(servicesDto.getScheduledTime());
            if (servicesDto.getServiceStatus() != null)
                serviceEntity.setServiceStatus(servicesDto.getServiceStatus());
            if (servicesDto.getMechanicsDto() != null)
                serviceEntity.setMechanics(mechanicMapper.toEntityList(servicesDto.getMechanicsDto()));

        }
        return serviceMapper.toServicesDto(serviceEntity);


    }

    public void deleteService(Integer id) {
        servicesRepository.deleteById(id);
        System.out.println("service " + id + "deleted");
    }
}
