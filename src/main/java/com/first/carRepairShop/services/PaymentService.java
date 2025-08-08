package com.first.carRepairShop.services;

import com.first.carRepairShop.dto.PaymentDto;
import com.first.carRepairShop.entity.PaymentStatus;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    PaymentDto createPayment(PaymentDto paymentdto);

    PaymentDto markPaymentAsCompleted(Integer paymentId);

    PaymentDto markPaymentAsFailed(Integer paymentId);

    PaymentDto markPaymentAsRefunded(Integer paymentId);

    PaymentDto getPaymentById(Integer paymentId);

    List<PaymentDto> getPaymentsByInvoiceId(Integer invoiceId);

    List<PaymentDto> getPaymentsByStatus(PaymentStatus paymentStatus);

    void canceledPayment(Integer paymentId);

    PaymentDto updatePaymentNotes(Integer paymentId, String notes);
}

