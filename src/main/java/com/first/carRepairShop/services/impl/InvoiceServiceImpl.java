package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.associations.ItemDetail;
import com.first.carRepairShop.associations.ServiceDetail;
import com.first.carRepairShop.dto.*;
import com.first.carRepairShop.entity.*;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.BusinessConflictException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.mapper.*;
import com.first.carRepairShop.repository.*;
import com.first.carRepairShop.services.InvoicePreparationService;
import com.first.carRepairShop.services.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final InvoicePreparationService invoicePreparationService;
    private final CreditNoteRepository creditNoteRepository;
    private final CreditNoteMapper creditNoteMapper;

    private static void calculateTotalAmounts(Invoice invoice, BigDecimal taxRate, BigDecimal discountRate) {
        BigDecimal totalServicesAmount = invoice.getServicesDetailList().stream()
                .map(ServiceDetail::getServicePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalItemsAmount = invoice.getItemsDetailList().stream()
                .map(ItemDetail::getItemPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal subAmount = totalServicesAmount.add(totalItemsAmount);
        BigDecimal taxAmount = subAmount.multiply(taxRate);
        BigDecimal discountAmount = subAmount.multiply(discountRate);
        BigDecimal totalAmount = (subAmount.add(taxAmount)).subtract(discountAmount);
        invoice.setItemAmount(totalItemsAmount);
        invoice.setServiceAmount(totalServicesAmount);
        invoice.setTaxRate(taxRate);
        invoice.setDiscountRate(discountRate);
        invoice.setTotalAmount(totalAmount);
    }

    public InvoiceDto createInvoice(CreateInvoiceDto createInvoiceDto) {
        if (createInvoiceDto.getRepairOrderId() == null) {
            throw new IllegalArgumentException("RepairOrder id  cannot be null");
        }
        InvoiceData invoiceData = invoicePreparationService.prepareInvoiceData(createInvoiceDto.getRepairOrderId());
        RepairOrder repairOrder = invoiceData.getRepairOrder();
        Customer customer = repairOrder.getCustomer();
        if (customer == null) {
            throw new BadRequestException("Customer is required for generate Invoice");
        }
        Car car = repairOrder.getCar();
        if (car == null) {
            throw new BadRequestException("Car is required for generate Invoice");
        }
        List<WorkLog> workLogList = invoiceData.getAssignment().getWorkLogs();
        List<List<ServiceDetail>> serviceDetailLists = workLogList.stream()
                .map(workLog -> workLog.getPerformedService())
                .collect(Collectors.toList());
        List<ServiceDetail> serviceDetailList1 = serviceDetailLists.stream()
                .flatMap(lists -> lists.stream())
                .collect(Collectors.toList());

        List<List<ItemDetail>> itemDetailLists = invoiceData.getWorkLogList().stream()
                .map(workLog -> workLog.getUsedItem())
                .collect(Collectors.toList());
        List<ItemDetail> itemDetailList1 = itemDetailLists.stream()
                .flatMap(lists -> lists.stream())
                .collect(Collectors.toList());

        Invoice invoice = Invoice.builder()
                .invoiceDate(LocalDate.now())
                .invoiceNumber(generateInvoiceNumber(LocalDate.now(), createInvoiceDto.getRepairOrderId()))
                .carId(car.getCarId())
                .numberPlate(car.getNumberPlate())
                .customer(customer)
                .repairOrder(repairOrder)
                .servicesDetailList(serviceDetailList1)
                .itemsDetailList(itemDetailList1)
                .invoiceStatus(InvoiceStatus.DRAFT)
                .createdBy(createInvoiceDto.getCreatedBy())
                .build();
        BigDecimal taxRate = new BigDecimal("0.10");
        calculateTotalAmounts(invoice, taxRate, createInvoiceDto.getDiscountRate());

        Invoice savedInvoice = invoiceRepository.save(invoice);
        return invoiceMapper.toDto(savedInvoice);
    }

    public InvoiceDto finalizeInvoice(Integer invoiceId, String finalizedBy) {
        if (invoiceId == null) {
            throw new BadRequestException("Invoice id cannot be null");
        }
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NotFoundException("No Invoice found with id: " + invoiceId));
        if (!invoice.getInvoiceStatus().equals(InvoiceStatus.DRAFT)) {
            throw new BusinessConflictException("Only draft invoices can be finalized.");
        }

        invoice.setInvoiceStatus(InvoiceStatus.FINALIZED);
        invoice.setFinalizedAt(LocalDateTime.now());
        invoice.setFinalizedBy(finalizedBy);
        Invoice invoice1 = invoiceRepository.save(invoice);
        return invoiceMapper.toDto(invoice1);
    }

    public InvoiceDto getInvoice(Integer id) {
        if (id == null) throw new BadRequestException("Invoice id is required");
        Invoice invoiceEntity = invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice with id:" + id + " not found"));
        return invoiceMapper.toDto(invoiceEntity);
    }

    public InvoiceDto getInvoiceByInvoiceNumber(String invoiceNumber) {
        if (invoiceNumber == null) throw new BadRequestException("Invoice Number is required");
        Invoice invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new NotFoundException("Invoice with number: " + invoiceNumber + " not found"));
        return invoiceMapper.toDto(invoice);
    }

    public void deleteInvoice(Integer id) {
        if (id == null) throw new BadRequestException("Invoice Id is required");
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(" No Invoice found with id :" + id));
        if (!InvoiceStatus.DRAFT.equals(invoice.getInvoiceStatus())) {
            throw new BusinessConflictException("Only invoices in DRAFT status can be deleted.");
        }
        invoiceRepository.delete(invoice);
        log.info("Invoice {} deleted", id);
    }

    public void deleteInvoiceByInvoiceNumber(String invoiceNumber) {
        if (invoiceNumber == null) {
            throw new IllegalArgumentException("invoiceNumber Can not be null");
        }
        Invoice invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new NotFoundException("No Invoice found with Number :" + invoiceNumber));
        if (!InvoiceStatus.DRAFT.equals(invoice.getInvoiceStatus())) {
            throw new BusinessConflictException("Only invoices in DRAFT status can be deleted.");
        }
        invoiceRepository.delete(invoice);
        log.info("Invoice with invoice number {} deleted", invoiceNumber);
    }

    public void cancelInvoice(Integer invoiceId) {
        if (invoiceId == null) {
            throw new BadRequestException("Invoice id is required");
        }
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NotFoundException("No Invoice found with id:" + invoiceId));
        if (!InvoiceStatus.FINALIZED.equals(invoice.getInvoiceStatus())) {
            throw new BusinessConflictException("Only FINALIZED Invoice can canceled");
        }
        invoice.setInvoiceStatus(InvoiceStatus.CANCELLED);
        invoice.setCancelAt(LocalDateTime.now());
        invoiceRepository.save(invoice);
        log.info("Invoice with id {} has been canceled", invoiceId);

    }

    public InvoiceDto findInvoiceWithCustomerId(Integer customerId) {
        if (customerId == null)
            throw new IllegalArgumentException("customerId cannot be null");
        Invoice invoice = invoiceRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new NotFoundException("No invoice found for customer id: " + customerId));
        return invoiceMapper.toDto(invoice);
    }

    private String generateInvoiceNumber(LocalDate invoiceDate, Integer repairOrderId) {
        boolean exist = invoiceRepository.existsByRepairOrder_RepairOrderId(repairOrderId);
        if (exist) {
            Invoice invoice = invoiceRepository.findInvoiceByRepairOrder_RepairOrderId(repairOrderId)
                    .orElseThrow(() -> new NotFoundException("No invoice found with this RepairOrder id:" + repairOrderId));
            String number = invoice.getInvoiceNumber();
            throw new BadRequestException(" Invoice for this RepairOrder already exist.Invoice Number: " + number);
        }
        String date = invoiceDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String sequenceKey = "invoice:counter:" + date;
        Long sequence = redisTemplate.opsForValue().increment(sequenceKey, 1);
        String formattedSequence = String.format("%03d", sequence);
        return "IN" + date + "-" + formattedSequence;
    }

    public CreditNoteDto createCreditNote(CreditNoteDto creditNoteDto) {
        if (creditNoteDto == null) throw new BadRequestException("credit Note is required");
        Invoice invoice = invoiceRepository.findById(creditNoteDto.getOriginalInvoiceId())
                .orElseThrow(() -> new NotFoundException("No Invoice found with id: " + creditNoteDto.getOriginalInvoiceId()));
        if (creditNoteDto.getAmount() == null || creditNoteDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Credit amount must be positive");
        }
        String creditNumber = generateCreditNoteNumber(LocalDate.now());
        if (creditNoteRepository.existsByCreditNoteNumber(creditNumber)) {
            throw new BusinessConflictException("Credit Note number already exists: " + creditNumber);
        }
        CreditNote creditNote = CreditNote.builder()
                .amount(creditNoteDto.getAmount())
                .originalInvoice(invoice)
                .creditNoteNumber(creditNumber)
                .build();
        CreditNote savedCreditNote = creditNoteRepository.save(creditNote);
        return creditNoteMapper.toDto(savedCreditNote);

    }

    private String generateCreditNoteNumber(LocalDate date) {
        String date1 = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String sequenceKey = "creditNote:counter:" + date1;
        Long sequence = redisTemplate.opsForValue().increment(sequenceKey, 1);
        String formattedSequence = String.format("%03d", sequence);
        return "CR" + date1 + "-" + formattedSequence;
    }

    public void markAsPaid(Integer invoiceId, String paymentInfo) {
        //todo
    }
}
