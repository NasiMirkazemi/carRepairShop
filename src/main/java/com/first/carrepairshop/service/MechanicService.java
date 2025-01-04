package com.first.carrepairshop.service;

import com.first.carrepairshop.dto.MechanicDto;
import com.first.carrepairshop.entity.Mechanic;
import com.first.carrepairshop.mapper.MechanicMapper;
import com.first.carrepairshop.mapper.ServiceMapper;
import com.first.carrepairshop.repository.MechanicRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MechanicService {
    private final MechanicRepository mechanicRepository;
    private final MechanicMapper mechanicMapper;
    private final ServiceMapper serviceMapper;

    public MechanicDto addMechanic(MechanicDto mechanicDto) {

        Mechanic mechanicEntity = mechanicRepository.save(mechanicMapper.toMechanicEntity(mechanicDto));
        return mechanicMapper.toMechanicDto(mechanicEntity);
    }

    public MechanicDto getMechanic(Integer id) {
        Mechanic mechanicEntity = mechanicRepository.findById(id).get();
        return mechanicMapper.toMechanicDto(mechanicEntity);

    }

    public MechanicDto updateMechanic(MechanicDto mechanicDto) {
        Optional<Mechanic> mechanicOptional = mechanicRepository.findById(mechanicDto.getEmployeeId());
        Mechanic mechanicEntity = null;
        if (mechanicOptional.isPresent()) {
            mechanicEntity = mechanicOptional.get();
            if (mechanicDto.getSpecialty() != null)
                mechanicEntity.setSpecialty(mechanicDto.getSpecialty());
            if (mechanicDto.getCertificate() != null)
                mechanicEntity.setCertificate(mechanicDto.getCertificate());
            if (mechanicDto.getHourlyRate() != null)
                mechanicEntity.setHourlyRate(mechanicDto.getHourlyRate());
            if (!mechanicDto.getServicesDto().isEmpty()) {
                mechanicEntity.getServices().clear();
                mechanicEntity.getServices().addAll(serviceMapper.toEntityList(mechanicDto.getServicesDto()));
            }
            mechanicRepository.save(mechanicEntity);
        }
        return mechanicMapper.toMechanicDto(mechanicEntity);
    }

    public void deleteMechanic(Integer id) {
        mechanicRepository.deleteById(id);
    }

}




