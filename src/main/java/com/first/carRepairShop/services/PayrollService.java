package com.first.carRepairShop.services;

import com.first.carRepairShop.dto.PayrollDto;
import com.first.carRepairShop.entity.Payroll;

import java.time.LocalDate;
import java.util.List;

public interface PayrollService {
    PayrollDto generatePayroll(Integer mechanicId, LocalDate start, LocalDate end);

    PayrollDto approvePayroll(Integer id, String username);

    PayrollDto getPayrollById(Integer payrollId);

    void deletePayroll(Integer payrollId);

    List<PayrollDto> payrollList(Integer userId);

}
