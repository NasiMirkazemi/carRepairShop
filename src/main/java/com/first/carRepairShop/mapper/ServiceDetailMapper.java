package com.first.carRepairShop.mapper;

import com.first.carRepairShop.associations.ServiceDetail;
import com.first.carRepairShop.dto.ServiceDetailDto;
import com.first.carRepairShop.dto.ServiceDto;
import com.first.carRepairShop.entity.Services;
import jakarta.mail.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Named;

import java.util.List;

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
    @Named("mapServicesToServiceDetailList")
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
    List<ServiceDetailDto> toServiceDetailDtoListFromServiceDto(List<ServiceDto> serviceDtoList);

    // ✅ Convert List<ServiceDetailDto> → List<ServiceDto>
    List<ServiceDto> toServiceDtoListFromServiceDetailDto(List<ServiceDetailDto> serviceDetailDtoList);

    // ✅ Convert ServiceDetailDto → ServiceDetail
    ServiceDetail toServiceDetail(ServiceDetailDto serviceDetailDto);

    // ✅ Convert ServiceDetail → ServiceDetailDto
    ServiceDetailDto toServiceDetailDto(ServiceDetail serviceDetail);

    // ✅ Convert List<ServiceDetailDto> → List<ServiceDetail>
    //111
    List<ServiceDetail> toServiceDetailList(List<ServiceDetailDto> serviceDetailDtoList);

    // ✅ Convert List<ServiceDetail> → List<ServiceDetailDto>
    @Named("mapServiceDetailListToServiceDetailDtoList")
    List<ServiceDetailDto> toServiceDetailDtoListFromServiceDetail(List<ServiceDetail> serviceDetailList);

    // ✅ Update an existing ServiceDetail from ServiceDetailDto
    @Mapping(target = "serviceId", ignore = true)
    void updateServiceDetailFromDto(ServiceDetailDto serviceDetailDto, @MappingTarget ServiceDetail serviceDetail);

    @Mapping(target = "serviceId", ignore = true)
    @Mapping(source = "serviceName", target = "serviceName")
    @Mapping(source = "servicePrice", target = "servicePrice")
    void updateServiceFromServiceDetailDto(ServiceDetailDto serviceDetailDto, @MappingTarget Services service);

    void updateServiceDetailFromServiceDetailDto(ServiceDetailDto serviceDetailDto, @MappingTarget ServiceDetail serviceDetail);

    @Named("mapServicesToServiceDetailDtoList")
    default List<ServiceDetailDto> toServiceDetailDtoFromServicesList(List<Services> services) {
        if (services == null || services.isEmpty()) {
            return null;
        }
        List<ServiceDetail> serviceDetailList = toServiceDetailListFromServices(services);
        return toServiceDetailDtoListFromServiceDetail(serviceDetailList);
    }

    @Named("mapServiceDetailDtoToServiceList")
    default List<Services> toServiceListFromServiceDetailDto(List<ServiceDetailDto> serviceDetailDtoList) {
        if (serviceDetailDtoList == null || serviceDetailDtoList.isEmpty()) {
            return null;
        }
        List<ServiceDetail> serviceDetailList = toServiceDetailList(serviceDetailDtoList);
        return toServiceListFromServiceDetail(serviceDetailList);
    }

}
