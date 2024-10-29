package com.first.carrepairshop.service;

import com.first.carrepairshop.dto.InvoiceDto;
import com.first.carrepairshop.entity.Invoice;
import com.first.carrepairshop.repository.InvoiceRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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
                .build()

        );
        invoiceDto.setInvoiceId(invoiceEntity.getInvoiceId());
        return invoiceDto;

    }

    public InvoiceDto update(InvoiceDto invoiceDto) {
        Invoice invoiceEntity = invoiceRepository.save(Invoice.builder()
                .invoiceId(invoiceDto.getInvoiceId())
                .invoiceNumber(invoiceDto.getInvoiceNumber())
                .totalAmount(invoiceDto.getTotalAmount())
                .carId(invoiceDto.getCarId())
                .customer(invoiceDto.getCustomer())
                .repairOrder(invoiceDto.getRepairOrder())
                .servicesList(invoiceDto.getServicesList())
                .items(invoiceDto.getItems())

                .build());
        return InvoiceDto.builder()
                .invoiceId(invoiceEntity.getInvoiceId())
                .invoiceNumber(invoiceEntity.getInvoiceNumber())
                .totalAmount(invoiceEntity.getTotalAmount())
                .carId(invoiceDto.getCarId())
                .customer(invoiceEntity.getCustomer())
                .repairOrder(invoiceEntity.getRepairOrder())
                .build();
    }

    public void removeInvoiceById(Integer id) {
        invoiceRepository.deleteById(id);
        System.out.println("invoice" + id + "deleted");
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


}
