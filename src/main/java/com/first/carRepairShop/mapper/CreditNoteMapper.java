package com.first.carRepairShop.mapper;

import com.first.carRepairShop.dto.CreditNoteDto;
import com.first.carRepairShop.entity.CreditNote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CreditNoteMapper {
    CreditNoteMapper INSTANCE = Mappers.getMapper(CreditNoteMapper.class);

    @Mapping(source = "originalInvoice.invoiceId", target = "originalInvoiceId")
    CreditNoteDto toDto(CreditNote creditNote);

    @Mapping(source = "originalInvoiceId", target = "originalInvoice.invoiceId")
    @Mapping(target = "creditNoteNumber", ignore = true) // Generated internally
    @Mapping(target = "issuedDate", ignore = true)
    CreditNote toEntity(CreditNoteDto creditNoteDto);

}

