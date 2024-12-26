package com.first.carrepairshop.controller;

import com.first.carrepairshop.dto.EmployeeDto;
import com.first.carrepairshop.entity.Employee;
import com.first.carrepairshop.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employeeController")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity<EmployeeDto> add(EmployeeDto employeeDto) {
        return ResponseEntity.ok(employeeService.addEmployee(employeeDto));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EmployeeDto> get(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    @PatchMapping("/update")
    public ResponseEntity<EmployeeDto> update(@RequestBody EmployeeDto employeeDto) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok("employee by id: " + id + "is deleted");
    }
}
