package com.first.carrepairshop.service;

import com.first.carrepairshop.dto.MechanicDto;
import com.first.carrepairshop.entity.Mechanic;
import com.first.carrepairshop.repository.MechanicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MechanicService {
    private final MechanicRepository mechanicRepository;

    public MechanicDto addMechanic(MechanicDto mechanicDto) {
        Mechanic mechanicEntity = mechanicRepository.save(Mechanic.builder()
                .name(mechanicDto.getName())
                .lastname(mechanicDto.getLastname())
                .age(mechanicDto.getAge())
                .role(mechanicDto.getRole())
                .phoneNumber(mechanicDto.getPhoneNumber())
                .salary(mechanicDto.getSalary())
                .specialty(mechanicDto.getSpecialty())
                .certificate(mechanicDto.getCertificate())
                .hourlyRate(mechanicDto.getHourlyRate())
                .services(mechanicDto.getServices())
                .build());
        mechanicDto.setEmployeeId(mechanicEntity.getEmployeeId());
        return mechanicDto;
    }

    public MechanicDto getMechanic(Integer id) {
        Mechanic mechanicEntity = mechanicRepository.findById(id).get();
        return MechanicDto.builder().specialty(mechanicEntity.getSpecialty())
                .certificate(mechanicEntity.getCertificate())
                .hourlyRate(mechanicEntity.getHourlyRate())
                .services(mechanicEntity.getServices()).build();


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
            if (!mechanicDto.getServices().isEmpty()) {
                mechanicEntity.getServices().clear();
                mechanicEntity.getServices().addAll(mechanicDto.getServices());
            }
            mechanicRepository.save(mechanicEntity);
        }
        return MechanicDto.builder()
                .specialty(mechanicEntity.getSpecialty())
                .certificate(mechanicEntity.getCertificate())
                .hourlyRate(mechanicEntity.getHourlyRate())
                .services(mechanicEntity.getServices())
                .build();
    }
    public void deleteMechanic (Integer id){
        mechanicRepository.deleteById(id);
    }


}




