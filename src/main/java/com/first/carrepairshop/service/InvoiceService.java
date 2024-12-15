package com.first.carrepairshop.service;

import com.first.carrepairshop.dto.InvoiceDto;
import com.first.carrepairshop.entity.Invoice;
import com.first.carrepairshop.repository.InvoiceRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceDto addInvoice(InvoiceDto invoiceDto) {
        Invoice invoiceEntity = invoiceRepository.save(Invoice.builder()
                .invoiceNumber(invoiceDto.getInvoiceNumber())
                .totalAmount(invoiceDto.getTotalAmount())
                .carId(invoiceDto.getCarId())
                .customer(invoiceDto.getCustomer())
                .repairOrder(invoiceDto.getRepairOrder())
                .servicesList(invoiceDto.getServicesList())
                .items(invoiceDto.getItems())
                .build());
        invoiceDto.setInvoiceId(invoiceEntity.getInvoiceId());
        return invoiceDto;
    }

    public InvoiceDto getInvoice(Integer id) {
        Invoice invoiceEntity = invoiceRepository.findById(id).get();
        return InvoiceDto.builder()
                .invoiceId(invoiceEntity.getInvoiceId())
                .invoiceNumber(invoiceEntity.getInvoiceNumber())
                .totalAmount(invoiceEntity.getTotalAmount())
                .carId(invoiceEntity.getCarId())
                .customer(invoiceEntity.getCustomer())
                .repairOrder(invoiceEntity.getRepairOrder())
                .build();
    }

    public InvoiceDto update(InvoiceDto invoiceDto) {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(invoiceDto.getInvoiceId());
        Invoice invoiceEntity = null;
        if (invoiceOptional.isPresent()) {
            invoiceEntity = invoiceOptional.get();
            if (invoiceDto.getInvoiceNumber() != null)
                invoiceEntity.setInvoiceNumber(invoiceDto.getInvoiceNumber());
            if (invoiceDto.getTotalAmount() != null)
                invoiceEntity.setTotalAmount(invoiceDto.getTotalAmount());
            if (invoiceDto.getCarId() != null)
                invoiceEntity.setCarId(invoiceDto.getCarId());
            if (invoiceDto.getCustomer() != null)
                invoiceEntity.setCustomer(invoiceDto.getCustomer());
            if (invoiceDto.getRepairOrder() != null)
                invoiceEntity.setRepairOrder(invoiceDto.getRepairOrder());
            invoiceRepository.save(invoiceEntity);
        }
        return InvoiceDto.builder()
                .invoiceId(invoiceEntity.getInvoiceId())
                .invoiceNumber(invoiceEntity.getInvoiceNumber())
                .totalAmount(invoiceEntity.getTotalAmount())
                .carId(invoiceDto.getCarId())
                .customer(invoiceEntity.getCustomer())
                .repairOrder(invoiceEntity.getRepairOrder())
                .build();
    }

    public void deleteInvoice(Integer id) {
        invoiceRepository.deleteById(id);
        System.out.println("invoice" + id + "deleted");
    }


}
