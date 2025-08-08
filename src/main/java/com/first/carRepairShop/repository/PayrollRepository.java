package com.first.carRepairShop.repository;

import com.first.carRepairShop.entity.Payroll;
import com.first.carRepairShop.entity.PayrollStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PayrollRepository extends JpaRepository<Payroll, Integer> {
    List<Payroll> findByUserId(Integer userId);

    List<Payroll> findByUserIdAndStatus(Integer userId, PayrollStatus status);

    List<Payroll> findByUserIdAndStartDateBetween(Integer userId, LocalDate start, LocalDate end);
}
