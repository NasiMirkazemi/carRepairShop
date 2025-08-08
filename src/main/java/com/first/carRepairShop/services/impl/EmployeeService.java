package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.dto.EmployeeDto;
import com.first.carRepairShop.entity.Employee;
import com.first.carRepairShop.entity.Role;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.exception.UserAlreadyExistsException;
import com.first.carRepairShop.mapper.EmployeeMapper;
import com.first.carRepairShop.mapper.RolesMapper;
import com.first.carRepairShop.repository.EmployeeRepository;
import com.first.carRepairShop.repository.RoleRepository;
import com.first.carRepairShop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        if (employeeDto == null || employeeDto.getUser() == null) {
            throw new BadRequestException("EmployeeDto  and User info cannot be null");
        }
        if (userRepository.existsByUsername(employeeDto.getUser().getUsername())) {
            throw new UserAlreadyExistsException("Username '" + employeeDto.getUser().getUsername() + "' is already taken");
        }
        Role role = roleRepository.findByName(employeeDto.getUser().getRole().getName())
                .orElseThrow(() -> new NotFoundException("No Role found with name: " + employeeDto.getUser().getRole().getName()));
        Employee employee = Employee.builder()
                .username(employeeDto.getUser().getUsername())
                .password(passwordEncoder.encode(employeeDto.getUser().getPassword()))
                .name(employeeDto.getUser().getName())
                .lastname(employeeDto.getUser().getLastname())
                .email(employeeDto.getUser().getEmail())
                .address(employeeDto.getUser().getAddress())
                .phone(employeeDto.getUser().getPhone())
                .role(role)
                .hireDate(LocalDate.now())
                .isEnable(true)
                .age(employeeDto.getAge())
                .department(employeeDto.getDepartment())
                .salary(employeeDto.getSalary())
                .build();


        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDto(savedEmployee);
    }

    public EmployeeDto getEmployee(Integer id) {
        if (id == null) throw new BadRequestException("Employee id is required");
        Employee employee = employeeRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Employee with id:" + id + " not found"));
        return employeeMapper.toDto(employee);

    }

    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        if (employeeDto == null) throw new BadRequestException("Employee info is required");
        if (employeeDto.getUser() == null || employeeDto.getUser().getId() == null) {
            throw new BadRequestException("Employee User ID is required");
        }
        Employee employee = employeeRepository.findById(employeeDto.getUser().getId())
                .orElseThrow(() -> new NotFoundException("No Employee found with id : " + employeeDto.getUser().getId()));
        Optional.ofNullable(employeeDto.getAge()).ifPresent(em -> employee.setAge(employeeDto.getAge()));
        Optional.ofNullable(employeeDto.getHireDate()).ifPresent(em -> employee.setHireDate(employeeDto.getHireDate()));
        Optional.ofNullable(employeeDto.getSalary()).ifPresent(em -> employee.setSalary(employeeDto.getSalary()));
        Optional.ofNullable(employeeDto.getDepartment()).ifPresent(em -> employee.setDepartment(employeeDto.getDepartment()));
        Optional.ofNullable(employeeDto.getUser()).ifPresent(userDto -> {
            employee.setUsername(userDto.getUsername());
            employee.setName(userDto.getName());
            employee.setLastname(userDto.getLastname());
            employee.setEmail(userDto.getEmail());
            employee.setPhone(userDto.getPhone());
            employee.setAddress(userDto.getAddress());
        });
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    public void deleteEmployeeById(Integer id) {
        if (id == null) throw new BadRequestException("Employee id is required");
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee with id:" + id + " not found"));
        employeeRepository.delete(employee);
        log.info("Employee with id {} has been deleted", employee.getId());

    }
}
