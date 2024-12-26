package com.first.carrepairshop.mapper;

import com.first.carrepairshop.dto.InvoiceDto;
import com.first.carrepairshop.entity.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
public interface InvoiceMapper {
    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);

    InvoiceDto toInvoiceDto(Invoice invoice);

    Invoice toInvoiceEntity(InvoiceDto invoiceDto);


}
