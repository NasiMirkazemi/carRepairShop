package com.first.carrepairshop.service;

import com.first.carrepairshop.dto.MechanicDto;
import com.first.carrepairshop.entity.Mechanic;
import com.first.carrepairshop.repository.MechanicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        mechanicDto.setId(mechanicEntity.getId());
        return mechanicDto;


    }}



