package com.first.carrepairshop.service;

import com.first.carrepairshop.dto.EmployeeDto;
import com.first.carrepairshop.entity.Employee;
import com.first.carrepairshop.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeRepository.save(Employee.builder().name(employeeDto.getName())
                .lastname(employeeDto.getLastname())
                .role(employeeDto.getRole())
                .age(employeeDto.getAge())
                .phoneNumber(employeeDto.getPhoneNumber())
                .salary(employeeDto.getSalary())
                .build());
        return employeeDto;
    }

    public EmployeeDto getEmployee(Integer id) {
        Employee employeeEntity = (Employee) employeeRepository.findById(id).get();
        return EmployeeDto.builder()
                .employeeId(employeeEntity.getEmployeeId())
                .name(employeeEntity.getName())
                .lastname(employeeEntity.getLastname())
                .age(employeeEntity.getAge())
                .role(employeeEntity.getRole())
                .salary(employeeEntity.getSalary())
                .phoneNumber(employeeEntity.getPhoneNumber())
                .build();


    }

    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeDto.getEmployeeId());
        Employee employeeEntity = null;
        if (employeeOptional.isPresent()) {
            employeeEntity = employeeOptional.get();
            if (employeeDto.getName() != null)
                employeeEntity.setName(employeeDto.getName());
            if (employeeDto.getLastname() != null)
                employeeEntity.setLastname(employeeDto.getLastname());
            if (employeeDto.getAge() != null)
                employeeEntity.setAge(employeeDto.getAge());
            if (employeeDto.getRole() != null)
                employeeEntity.setRole(employeeDto.getRole());
            if (employeeDto.getSalary() != null)
                employeeEntity.setSalary(employeeDto.getSalary());
            if (employeeDto.getPhoneNumber() != null)
                employeeEntity.setPhoneNumber(employeeDto.getPhoneNumber());
        }
        return EmployeeDto.builder()
                .employeeId(employeeEntity.getEmployeeId())
                .name(employeeEntity.getName())
                .lastname(employeeEntity.getLastname())
                .age(employeeEntity.getAge())
                .role(employeeEntity.getRole())
                .salary(employeeEntity.getSalary())
                .phoneNumber(employeeEntity.getPhoneNumber())
                .build();
    }

    public void deleteEmployeeById(Integer id) {
        employeeRepository.deleteById(id);
        System.out.println("employee" + id + "deleted");
    }
}
