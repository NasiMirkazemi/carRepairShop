package com.first.carRepairShop.repository;

import com.first.carRepairShop.dto.PaymentDto;
import com.first.carRepairShop.entity.Payment;
import com.first.carRepairShop.entity.PaymentStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findAllByPaymentDate(LocalDate paymentDate);

    Optional<Payment> findByInvoiceNumber(String invoiceNumber);

    List<Payment> findAllByInvoice_InvoiceId( Integer invoiceId);
    List<Payment> findAllByPaymentStatus(PaymentStatus paymentStatus);
}
