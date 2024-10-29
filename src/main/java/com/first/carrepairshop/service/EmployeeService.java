package com.first.carrepairshop.service;

import com.first.carrepairshop.dto.EmployeeDto;
import com.first.carrepairshop.entity.Employee;
import com.first.carrepairshop.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public  void deleteEmployeeById(Integer id){
        employeeRepository.deleteById(id);
        System.out.println("employee"+id+"deleted");
    }
    public EmployeeDto getEmployee(Integer id ){
         Employee employeeEntity= (Employee) employeeRepository.findById(id).get();
          return EmployeeDto.employeeDtoBuilder()
                 .id(employeeEntity.getId())
                 .name(employeeEntity.getName())
                 .lastname(employeeEntity.getLastname())
                 .age(employeeEntity.getAge())
                 .role(employeeEntity.getRole())
                 .salary(employeeEntity.getSalary())
                 .phoneNumber(employeeEntity.getPhoneNumber())
                 .build();


    }
    public EmployeeDto updateEmployee( EmployeeDto employeeDto){
       Employee employeeEntity= (Employee) employeeRepository.save(Employee.builder()
                        .id(employeeDto.getId())
                        .name(employeeDto.getName())
                        .lastname(employeeDto.getLastname())
                        .age(employeeDto.getAge())
                        .role(employeeDto.getRole())
                        .salary(employeeDto.getSalary())
                        .phoneNumber(employeeDto.getPhoneNumber())
                .build());
        return EmployeeDto.employeeDtoBuilder()
               .id(employeeEntity.getId())
               .name(employeeEntity.getName())
               .lastname(employeeEntity.getLastname())
               .age(employeeEntity.getAge())
               .role(employeeEntity.getRole())
               .salary(employeeEntity.getSalary())
               .phoneNumber(employeeEntity.getPhoneNumber())
               .build();
    }
}
