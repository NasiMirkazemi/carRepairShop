package com.first.carRepairShop.mapper;

import com.first.carRepairShop.dto.WorkLogDto;
import com.first.carRepairShop.entity.WorkLog;
import com.first.carRepairShop.entity.WorkLogSource;
import com.first.carRepairShop.entity.WorkLogStatus;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkLogMapper {
    WorkLogMapper INSTANCE = Mappers.getMapper(WorkLogMapper.class);

    @Named("enumToString")
    static String mapWorkLogStatusToString(WorkLogStatus workLogStatus) {
        return workLogStatus != null ? workLogStatus.name() : null;
    }

    @Named("stringToEnum")
    static WorkLogStatus mapStringToStatus(String status) {
        return status != null ? WorkLogStatus.valueOf(status.toUpperCase()) : null;

    }

    @Named("stringToEnumSource")
    static WorkLogSource mapStringToSource(String workLogSource) {
        //condition ? valueIfTrue : valueIfFalse
        return workLogSource != null ? WorkLogSource.valueOf(workLogSource.toUpperCase()) : null;
    }

    @Named("enumSourceToString")
    static String mapSourceToString(WorkLogSource workLogSource) {
        return workLogSource != null ? workLogSource.name() : null;
    }

    @Mapping(source = "workLogId", target = "workLogId")
    @Mapping(source = "performedService", target = "performedService")
    @Mapping(source = "usedItem", target = "usedItem")
    @Mapping(source = "assignment.assignmentId", target = "assignmentId")
    @Mapping(source = "mechanic.id", target = "mechanicId") // ✅ ADD THIS LINE
    @Mapping(source = "workLogStatus", target = "workLogStatus", qualifiedByName = "enumToString")
    @Mapping(source = "workLogSource", target = "workLogSource", qualifiedByName = "enumSourceToString")
    WorkLogDto toDto(WorkLog workLog);

    @Mapping(source = "performedService", target = "performedService")
    @Mapping(source = "usedItem", target = "usedItem")
    @Mapping(source = "assignmentId", target = "assignment.assignmentId")
    @Mapping(source = "workLogStatus", target = "workLogStatus", qualifiedByName = "stringToEnum")
    @Mapping(source = "workLogSource", target = "workLogSource", qualifiedByName = "stringToEnumSource")
    WorkLog toEntity(WorkLogDto workLogDto);

    List<WorkLog> toEntityList(List<WorkLogDto> workLogDtoList);

    List<WorkLogDto> toDtoList(List<WorkLog> workLogList);
}
