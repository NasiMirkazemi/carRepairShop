package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.dto.PayrollDto;
import com.first.carRepairShop.entity.*;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.mapper.PayrollMapper;
import com.first.carRepairShop.repository.MechanicRepository;
import com.first.carRepairShop.repository.PayrollRepository;
import com.first.carRepairShop.repository.UserRepository;
import com.first.carRepairShop.services.PayrollService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayrollServiceImpl implements PayrollService {
    private final PayrollRepository payrollRepository;
    private final MechanicRepository mechanicRepository;
    private final UserRepository userRepository;
    private final PayrollMapper payrollMapper;

    @Override
    public PayrollDto generatePayroll(Integer mechanicId, LocalDate start, LocalDate end) {
        if (mechanicId == null) throw new BadRequestException("Mechanic id is required");
        if (start == null || end == null) throw new BadRequestException("Start date and End date is required");
        Mechanic mechanic = mechanicRepository.findById(mechanicId)
                .orElseThrow(() -> new NotFoundException("No Mechanic found with id: " + mechanicId));
        List<WorkLog> workLogList = mechanic.getWorkLogs();
        BigDecimal totalHours = workLogList.stream()
                .filter(workLog -> workLog.getWorkLogStatus().equals(WorkLogStatus.COMPLETED))
                .filter(workLog -> !workLog.getCheckIn().toLocalDate().isBefore(start)
                        && !workLog.getCheckOut().toLocalDate().isAfter(end))
                .map(workLog -> BigDecimal.valueOf(workLog.getTotalHours() != null ? workLog.getTotalHours() : 0.0))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal grossPay = mechanic.getHourlyRate().multiply(totalHours);
        BigDecimal deductions = grossPay.multiply(BigDecimal.valueOf(0.15));
        BigDecimal netPay = grossPay.subtract(deductions);

        Payroll payroll = Payroll.builder()
                .mechanic(mechanic)
                .startDate(start)
                .endDate(end)
                .totalHoursWorked(totalHours.doubleValue())
                .hourlyRate(mechanic.getHourlyRate())
                .grossPay(grossPay)
                .deductions(deductions)
                .netPay(netPay)
                .issuedAt(LocalDateTime.now())
                .status(PayrollStatus.DRAFT)
                .build();
        return payrollMapper.toDto(payroll);
    }

    @Override
    public PayrollDto approvePayroll(Integer payrollId, String username) {
        if (payrollId == null) throw new BadRequestException("Payroll id is required");
        if (username == null) throw new BadRequestException("admin id is required");
        Payroll payroll = payrollRepository.findById(payrollId)
                .orElseThrow(() -> new NotFoundException("No payroll found with id: " + payrollId));
        UserApp userApp = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("No User found with username: " + username));
        if (payroll.getStatus() == PayrollStatus.APPROVED) {
            throw new BadRequestException("Payroll is already approved");
        }
        payroll.setStatus(PayrollStatus.APPROVED);
        payroll.setApprovedBy(userApp);
        payroll.setApprovedAt(LocalDateTime.now());
        Payroll savedPayroll = payrollRepository.save(payroll);

        return payrollMapper.toDto(savedPayroll);
    }


    @Override
    public PayrollDto getPayrollById(Integer payrollId) {
        if (payrollId == null) throw new BadRequestException("Payroll id is required");
        Payroll payroll = payrollRepository.findById(payrollId)
                .orElseThrow(() -> new NotFoundException("No Payroll found with id: " + payrollId));
        return payrollMapper.toDto(payroll);
    }

    @Override
    public void deletePayroll(Integer payrollId) {
        if (payrollId == null) throw new BadRequestException("Payroll id is required");
        Payroll payroll = payrollRepository.findById(payrollId)
                .orElseThrow(() -> new NotFoundException("No Payroll found with id: " + payrollId));

        payrollRepository.delete(payroll);
        log.info("Payroll with id {} has been deleted", payroll.getId());
    }

    @Override
    public List<PayrollDto> payrollList(Integer userId) {
        if (userId == null) throw new BadRequestException("User id is required");
        List<Payroll> payrollList = payrollRepository.findByUserId(userId);
        return payrollList.stream()
                .map(payrollMapper::toDto)
                .collect(Collectors.toList());

    }
}
