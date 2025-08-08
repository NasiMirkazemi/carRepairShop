package com.first.carRepairShop.services;

import com.first.carRepairShop.dto.ItemDetailDto;
import com.first.carRepairShop.dto.ServiceDetailDto;
import com.first.carRepairShop.dto.TotalHoursForWorKLog;
import com.first.carRepairShop.dto.WorkLogDto;
import com.first.carRepairShop.entity.WorkLog;
import com.first.carRepairShop.entity.WorkLogStatus;

import java.time.LocalDate;
import java.util.List;

public interface WorkLogService {
    WorkLogDto addWorkLog(WorkLogDto workLogDto);

    void startWorkLog(Integer workLogId);

    void pauseWorkLog(Integer workLogId);

    void resumeWorkLog(Integer workLogId);

    void completeWorkLog(Integer workLogId);

    void cancelWorkLog(Integer workLogId);

    ItemDetailDto addToUsedItem(Integer workLogId, ItemDetailDto itemDetailDto,int quantityUsed);

    ServiceDetailDto addServiceToPerformedServices(Integer workLogId, ServiceDetailDto serviceDetailDto);

    WorkLogDto getWorkLogById(Integer workLogId);
    List<WorkLogDto> getAllWorkLog();
    List<WorkLogDto> listWorkLogsByStatus(WorkLogStatus workLogStatus);
    List<WorkLogDto> listWorkLogsByDateRange(LocalDate from, LocalDate to);
    List<WorkLogDto> listWorkLogsByMechanic(Integer mechanicId);
    TotalHoursForWorKLog calculateTotalHourForView(Integer workLogId);
}
