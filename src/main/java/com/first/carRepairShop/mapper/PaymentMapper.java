package com.first.carRepairShop.mapper;

import com.first.carRepairShop.dto.PaymentDto;
import com.first.carRepairShop.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mapping(target = "invoiceId", source = "invoice.invoiceId")
    @Mapping(target = "customerId", source = "customer.id")
    PaymentDto toPaymentDto(Payment payment);

    @Mapping(target = "invoice.invoiceId", source = "invoiceId")
    @Mapping(target = "customer.id", source = "customerId")
    Payment toPaymentEntity(PaymentDto paymentDto);

    List<PaymentDto> toPaymentDtoList(List<Payment> paymentList);

    List<Payment> toPaymentEntityList(List<PaymentDto> paymentDtoList);
}
