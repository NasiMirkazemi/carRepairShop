package com.first.carRepairShop.mapper;

import com.first.carRepairShop.dto.PayrollDto;
import com.first.carRepairShop.entity.Payroll;
import com.first.carRepairShop.entity.PayrollStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PayrollMapper {
    Payroll INSTANCE = Mappers.getMapper(Payroll.class);

    @Named("enumToString")
    static String statusToString(PayrollStatus payrollStatus) {
        return payrollStatus != null ? payrollStatus.name() : null;

    }

    @Named("stringToEnum")
    static PayrollStatus stringToStatus(String payrollStatus) {
        return payrollStatus != null ? PayrollStatus.valueOf(payrollStatus.toUpperCase()) : null;
    }

    @Mapping(source = "status", target = "status", qualifiedByName = "enumToString")
    PayrollDto toDto(Payroll payroll);

    @Mapping(source = "status", target = "status", qualifiedByName = "stringToEnum")
    Payroll toEntity(PayrollDto payrollDto);
}
