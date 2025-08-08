package com.first.carRepairShop.mapper;

import com.first.carRepairShop.associations.ServiceDetail;
import com.first.carRepairShop.dto.ServiceDetailDto;
import com.first.carRepairShop.dto.ServiceDto;
import com.first.carRepairShop.entity.Services;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
@Mapper(componentModel = "spring")
public interface ServiceDetailMapper {
    ServiceDetailMapper INSTANCE = Mappers.getMapper(ServiceDetailMapper.class);

    // ✅ Convert Services → ServiceDetail
    @Mapping(source = "serviceId", target = "serviceId")
    @Mapping(source = "serviceName", target = "serviceName")
    @Mapping(source = "servicePrice", target = "servicePrice")
    ServiceDetail toServiceDetail(Services service);

    // ✅ Convert ServiceDetail → Services
    @Mapping(source = "serviceId", target = "serviceId")
    @Mapping(source = "serviceName", target = "serviceName")
    @Mapping(source = "servicePrice", target = "servicePrice")
    @Mapping(target = "description", ignore = true)
    Services toService(ServiceDetail serviceDetail);

    // ✅ Convert List<Services> → List<ServiceDetail>
    List<ServiceDetail> toServiceDetailListFromServices(List<Services> serviceList);

    // ✅ Convert List<ServiceDetail> → List<Services>
    List<Services> toServiceListFromServiceDetail(List<ServiceDetail> serviceDetailList);

    // ✅ Convert ServiceDto → ServiceDetailDto
    @Mapping(source = "serviceId", target = "serviceId")
    @Mapping(source = "serviceName", target = "serviceName")
    @Mapping(source = "servicePrice", target = "servicePrice")
    ServiceDetailDto toServiceDetailDto(ServiceDto serviceDto);

    // ✅ Convert ServiceDetailDto → ServiceDto
    @Mapping(source = "serviceId", target = "serviceId")
    @Mapping(source = "serviceName", target = "serviceName")
    @Mapping(source = "servicePrice", target = "servicePrice")
    @Mapping(target = "description", ignore = true)
    ServiceDto toServiceDto(ServiceDetailDto serviceDetailDto);

    // ✅ Convert List<ServiceDto> → List<ServiceDetailDto>
    @Named("mapServiceDetailDtoToServiceDtoList")
    List<ServiceDetailDto> toServiceDetailDtoListFromServiceDto(List<ServiceDto> serviceDtoList);

    // ✅ Convert List<ServiceDetailDto> → List<ServiceDto>
    List<ServiceDto> toServiceDtoListFromServiceDetailDto(List<ServiceDetailDto> serviceDetailDtoList);

    // ✅ Convert ServiceDetailDto → ServiceDetail
    ServiceDetail toServiceDetail(ServiceDetailDto serviceDetailDto);

    // ✅ Convert ServiceDetail → ServiceDetailDto
    ServiceDetailDto toServiceDetailDto(ServiceDetail serviceDetail);

    // ✅ Convert List<ServiceDetailDto> → List<ServiceDetail>
    List<ServiceDetail> toServiceDetailList(List<ServiceDetailDto> serviceDetailDtoList);

    // ✅ Convert List<ServiceDetail> → List<ServiceDetailDto>
    List<ServiceDetailDto> toServiceDetailDtoListFromServiceDetail(List<ServiceDetail> serviceDetailList);

    // ✅ Update an existing ServiceDetail from ServiceDetailDto
    @Mapping(target = "serviceId", ignore = true)
    void updateServiceDetailFromDto(ServiceDetailDto serviceDetailDto, @MappingTarget ServiceDetail serviceDetail);

}
