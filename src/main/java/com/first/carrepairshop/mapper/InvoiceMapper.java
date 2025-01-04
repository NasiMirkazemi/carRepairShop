package com.first.carrepairshop.mapper;

import com.first.carrepairshop.dto.InvoiceDto;
import com.first.carrepairshop.entity.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);

    @Mapping(target = "servicesDetailList", source = "servicesDetailList")
    @Mapping(target = "itemsDetailList", source = "itemsDetailList")
    @Mapping(target = "customerDto", source = "customer")
    @Mapping(target = "repairOrderDto", source = "repairOrder")
    InvoiceDto toInvoiceDto(Invoice invoice);

    @Mapping(target = "servicesDetailList", source = "servicesDetailList")
    @Mapping(target = "itemsDetailList", source = "itemsDetailList")
    @Mapping(target = "customer", source = "customerDto")
    @Mapping(target = "repairOrder", source = "repairOrderDto")
    Invoice toInvoiceEntity(InvoiceDto invoiceDto);


}
