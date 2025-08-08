package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.dto.PaymentDto;
import com.first.carRepairShop.entity.Customer;
import com.first.carRepairShop.entity.Invoice;
import com.first.carRepairShop.entity.Payment;
import com.first.carRepairShop.entity.PaymentStatus;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.mapper.PaymentMapper;
import com.first.carRepairShop.repository.CustomerRepository;
import com.first.carRepairShop.repository.InvoiceRepository;
import com.first.carRepairShop.repository.PaymentRepository;
import com.first.carRepairShop.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final RedisTemplate redisTemplate;
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public PaymentDto createPayment(PaymentDto paymentdto) {
        if (paymentdto == null) throw new BadRequestException("Payment Info is required");
        Invoice invoice = invoiceRepository.findById(paymentdto.getInvoiceId())
                .orElseThrow(() -> new NotFoundException("No Invoice found with id: " + paymentdto.getInvoiceId()));
        Customer customer = customerRepository.findById(paymentdto.getCustomerId())
                .orElseThrow(() -> new NotFoundException("NO Customer found with id: " + paymentdto.getCustomerId()));
        PaymentStatus paymentStatus = paymentdto.getPaymentMethod().isInstant()
                ? PaymentStatus.COMPLETED
                : PaymentStatus.PENDING;

        Payment payment = Payment.builder()
                .paymentDate(LocalDate.now())
                .paymentNumber(generatePaymentNumber(LocalDate.now()))
                .paymentStatus(paymentStatus)
                .invoice(invoice)
                .invoiceNumber(invoice.getInvoiceNumber())
                .customer(customer)
                .amount(paymentdto.getAmount())
                .notes(paymentdto.getNotes())
                .paymentMethod(paymentdto.getPaymentMethod())
                .build();
        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toPaymentDto(savedPayment);

    }

    @Override
    @Transactional
    public PaymentDto markPaymentAsCompleted(Integer paymentId) {
        if (paymentId == null) throw new BadRequestException("Payment id is required");
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("No Payment found with id: " + paymentId));
        if (payment.getPaymentStatus().equals(PaymentStatus.COMPLETED)) {
            throw new BadRequestException("Payment is already Completed");
        }
        if (!payment.getPaymentStatus().equals(PaymentStatus.COMPLETED)) {
            payment.setPaymentStatus(PaymentStatus.COMPLETED);
        }
        return paymentMapper.toPaymentDto(payment);
    }

    @Override
    public PaymentDto markPaymentAsFailed(Integer paymentId) {
        if (paymentId == null) throw new BadRequestException("Payment id is required");
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("No Payment found with id: " + paymentId));
        if (payment.getPaymentStatus().equals(PaymentStatus.FAILED)) {
            throw new BadRequestException("Payment is already FAILED");
        }
        if (payment.getPaymentStatus() != PaymentStatus.PENDING) {
            throw new BadRequestException("Only PENDING payments can be marked as FAILED");
        }
        payment.setPaymentStatus(PaymentStatus.FAILED);
        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toPaymentDto(savedPayment);

    }

    @Override
    public PaymentDto markPaymentAsRefunded(Integer paymentId) {
        if (paymentId == null) throw new BadRequestException("Payment id is required");
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("No Payment found with id: " + paymentId));
        if (payment.getPaymentStatus().equals(PaymentStatus.REFUNDED)) {
            throw new BadRequestException("Payment is already refunded");
        }
        if (!payment.getPaymentStatus().equals(PaymentStatus.COMPLETED)) {
            throw new BadRequestException("Only COMPLETED payments can be refunded");
        }
        payment.setPaymentStatus(PaymentStatus.REFUNDED);
        Payment savedPayment = paymentRepository.save(payment);

        return paymentMapper.toPaymentDto(savedPayment);

    }

    @Override
    public PaymentDto getPaymentById(Integer paymentId) {
        if (paymentId == null) throw new BadRequestException("Payment id is required");
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("No Payment found with id: " + paymentId));
        return paymentMapper.toPaymentDto(payment);

    }

    @Override
    public List<PaymentDto> getPaymentsByInvoiceId(Integer invoiceId) {
        if (invoiceId == null) throw new BadRequestException("Invoice id is required");
        List<Payment> paymentList = paymentRepository.findAllByInvoice_InvoiceId(invoiceId);
        if (paymentList.isEmpty()) {
            throw new NotFoundException("No Payment found for Invoice with id: " + invoiceId);
        }
        return paymentMapper.toPaymentDtoList(paymentList);
    }

    @Override
    public List<PaymentDto> getPaymentsByStatus(PaymentStatus paymentStatus) {
        if (paymentStatus == null) throw new BadRequestException("Payment Status is required");
        List<Payment> paymentList = paymentRepository.findAllByPaymentStatus(paymentStatus);
        if (paymentList.isEmpty()) {
            throw new NotFoundException("No Payments found for payment status: " + paymentStatus);
        }
        return paymentMapper.toPaymentDtoList(paymentList);
    }

    @Override
    public void canceledPayment(Integer paymentId) {
        if (paymentId == null) throw new BadRequestException("Payment Id is required");
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("No payment found with id: " + paymentId));
        if (payment.getPaymentStatus() == PaymentStatus.CANCELED) {
            throw new BadRequestException("Payment is already canceled");
        }
        if (payment.getPaymentStatus() != PaymentStatus.PENDING) {
            throw new BadRequestException("Only PENDING payments can be canceled");
        }
        payment.setPaymentStatus(PaymentStatus.CANCELED);
        paymentRepository.save(payment);
        log.info("Payment with id {} has been canceled", paymentId);
    }

    @Override
    public PaymentDto updatePaymentNotes(Integer paymentId, String notes) {
        if (paymentId == null) throw new BadRequestException("Payment Id is required");
        if (notes == null || notes.isEmpty()) throw new BadRequestException("Notes is required");
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("No payment found with id: " + paymentId));
        payment.setNotes(notes);
        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toPaymentDto(savedPayment);
    }

    private String generatePaymentNumber(LocalDate localDate) {
        String date = localDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        String redisKey = "payment_seq:" + date;
        Long sequence = redisTemplate.opsForValue().increment(redisKey);
        if (sequence == 1) {
            redisTemplate.expire(redisKey, Duration.ofDays(1));
        }
        return String.format("PYM-%s-%04d", date, sequence);
    }
}
