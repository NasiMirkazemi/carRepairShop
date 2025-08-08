package com.first.carRepairShop.mapper;

import com.first.carRepairShop.dto.AssignmentDto;
import com.first.carRepairShop.dto.AssignmentDtoFull;
import com.first.carRepairShop.entity.Assignment;
import com.first.carRepairShop.entity.AssignmentStatus;
import com.first.carRepairShop.entity.WorkLogStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {

    AssignmentMapper INSTANCE = Mappers.getMapper(AssignmentMapper.class);

    @Named("enumToString")
    static String mapWorkLogStatusToString(AssignmentStatus assignmentStatus) {
        return assignmentStatus != null ? assignmentStatus.name() : null;
    }

    @Named("StringToEnum")
    static AssignmentStatus mapStringToStatus(String status) {
        return status != null ? AssignmentStatus.valueOf(status.toUpperCase()) : null;

    }


    @Mapping(source = "workLogs", target = "workLogs")
    @Mapping(source = "repairOrder.repairOrderId", target = "repairOrderId")
    @Mapping(source = "assignmentStatus", target = "assignmentStatus", qualifiedByName = "enumToString")
    AssignmentDto toDto(Assignment assignment);

    @Mapping(source = "workLogs", target = "workLogs")
    @Mapping(source = "repairOrderId", target = "repairOrder.repairOrderId")
    @Mapping(source = "assignmentStatus", target = "assignmentStatus", qualifiedByName = "StringToEnum")
    Assignment toEntity(AssignmentDto assignmentDto);

    @Mapping(source = "workLogs", target = "workLogs")
    AssignmentDtoFull toDtoFull(Assignment assignment);

    @Mapping(source = "workLogs", target = "workLogs")
    Assignment toEntityFromFull(AssignmentDtoFull assignmentDtoFull);

    List<Assignment> toEntityList(List<AssignmentDto> assignmentDtoList);

    List<AssignmentDto> toDtoList(List<Assignment> assignmentList);


}
