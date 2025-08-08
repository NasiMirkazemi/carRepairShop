package com.first.carRepairShop.mapper;

import com.first.carRepairShop.dto.AppointmentDto;
import com.first.carRepairShop.entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    AppointmentDto toAppointmentDto(Appointment appointment);

    Appointment toAppointmentEntity(AppointmentDto appointmentDto);

    List<AppointmentDto> toAppointmentDtoList(List<Appointment> appointments);

    List<Appointment> toAppointmentEntityList(List<AppointmentDto> appointmentDtos);
    @Mapping(target = "appointmentId", ignore = true) // Prevents overwriting the ID
    void updateAppointmentFromDto(AppointmentDto appointmentDto, @MappingTarget Appointment appointment);

}
