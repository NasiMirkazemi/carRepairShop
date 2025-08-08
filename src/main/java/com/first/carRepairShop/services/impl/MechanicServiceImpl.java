package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.dto.MechanicDto;
import com.first.carRepairShop.dto.MechanicRegister;
import com.first.carRepairShop.dto.UserDto;
import com.first.carRepairShop.dto.WorkLogDto;
import com.first.carRepairShop.entity.Mechanic;
import com.first.carRepairShop.entity.Role;
import com.first.carRepairShop.entity.UserApp;
import com.first.carRepairShop.entity.WorkLog;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.exception.UserAlreadyExistsException;
import com.first.carRepairShop.mapper.*;
import com.first.carRepairShop.repository.MechanicRepository;
import com.first.carRepairShop.repository.RoleRepository;
import com.first.carRepairShop.repository.UserRepository;
import com.first.carRepairShop.services.MechanicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MechanicServiceImpl implements MechanicService {
    private final MechanicRepository mechanicRepository;
    private final MechanicMapper mechanicMapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WorkLogMapper workLogMapper;


    public MechanicRegister addMechanic(MechanicRegister mechanicRegister) {
        if (mechanicRegister == null || mechanicRegister.getUser() == null)
            throw new BadRequestException("Mechanic user info is required");

        // Mechanic mechanic = mechanicMapper.toEntityFromRegister(mechanicRegister);
        if (userRepository.existsByUsername(mechanicRegister.getUser().getUsername()))
            throw new UserAlreadyExistsException("Username '" + mechanicRegister.getUser().getUsername() + "' is already taken");

        Role role = roleRepository.findByName(mechanicRegister.getUser().getRole().getName().toUpperCase())
                .orElseThrow(() -> new NotFoundException("No Role found with name :" + mechanicRegister.getUser().getRole().getName()));
        Mechanic mechanic = Mechanic.builder()
                .password(passwordEncoder.encode(mechanicRegister.getUser().getPassword()))
                .username(mechanicRegister.getUser().getUsername())
                .name(mechanicRegister.getUser().getName())
                .lastname(mechanicRegister.getUser().getLastname())
                .age(mechanicRegister.getAge())
                .email(mechanicRegister.getUser().getEmail())
                .phone(mechanicRegister.getUser().getPhone())
                .address(mechanicRegister.getUser().getAddress())
                .role(role)
                .isEnable(true)
                .certificate(mechanicRegister.getCertificate())
                .department(mechanicRegister.getDepartment())
                .hireDate(LocalDate.now())
                .salary(mechanicRegister.getSalary())
                .specialty(mechanicRegister.getSpecialty())
                .build();

        Mechanic mechanicUser = mechanicRepository.save(mechanic);
        return mechanicMapper.toMechanicRegister(mechanicUser);

    }

    @Override
    public void deleteMechanic(Integer mechanicId) {
        if (mechanicId == null)
            throw new BadRequestException("Mechanic Id is required");
        Mechanic mechanic = mechanicRepository.findById(mechanicId)
                .orElseThrow(() -> new NotFoundException("No Mechanic found with id: " + mechanicId));
        UserApp user = userRepository.findById(mechanicId)
                .orElseThrow(() -> new NotFoundException("No User found whit this id"));
        user.setEnable(false);
        mechanicRepository.delete(mechanic);
        log.info("Mechanic with id {} has been deleted", mechanic.getId());
    }

    @Override
    public MechanicDto updateMechanic(MechanicDto mechanicDto) {
        if (mechanicDto == null) throw new BadRequestException("Mechanic info is required");
        Mechanic mechanic = mechanicRepository.findById(mechanicDto.getUser().getId())
                .orElseThrow(() -> new NotFoundException("No mechanic found with id: " + mechanicDto.getUser().getId()));
        if (mechanicDto.getUser() == null || mechanicDto.getUser().getId() == null) {
            throw new BadRequestException("Mechanic user id is required");
        }
        Optional.ofNullable(mechanicDto.getUser()).ifPresent(userDto -> {
            mechanic.setUsername(userDto.getUsername());
            mechanic.setName(userDto.getName());
            mechanic.setLastname(userDto.getLastname());
            mechanic.setEmail(userDto.getEmail());
            mechanic.setPhone(userDto.getPhone());
            mechanic.setAddress(userDto.getAddress());
        });
        Optional.ofNullable(mechanicDto.getAge()).ifPresent(age -> mechanic.setAge(age));
        Optional.ofNullable(mechanicDto.getSalary()).ifPresent(salary -> mechanic.setSalary(salary));
        Optional.ofNullable(mechanicDto.getSpecialty()).ifPresent(specialty -> mechanic.setSpecialty(specialty));
        Optional.ofNullable(mechanicDto.getCertificate()).ifPresent(certificate -> mechanic.setCertificate(certificate));
        Optional.ofNullable(mechanicDto.getDepartment()).ifPresent(department -> mechanic.setDepartment(department));
        Optional.ofNullable(mechanicDto.getHourlyRate()).ifPresent(hourlyRate -> mechanic.setHourlyRate(hourlyRate));
        Mechanic savedMechanic = mechanicRepository.save(mechanic);
        return mechanicMapper.toDto(savedMechanic);
    }

    @Override
    public List<MechanicDto> getAllMechanic() {
        List<Mechanic> mechanicList = mechanicRepository.findAll();
        return mechanicMapper.toMechanicDtoList(mechanicList);
    }

    @Override
    public List<WorkLogDto> getAllWorkLog(Integer mechanicId) {
        if (mechanicId == null) throw new BadRequestException("Mechanic id is required");
        Mechanic mechanic = mechanicRepository.findById(mechanicId)
                .orElseThrow(() -> new NotFoundException("No mechanic found with this id"));
        List<WorkLog> workLogList = mechanicRepository.findWorkLogByMechanicId(mechanic.getId());
        if (workLogList.isEmpty()) {
            log.info("No work logs found.");
        }
        return workLogMapper.toDtoList(workLogList);
    }


}




