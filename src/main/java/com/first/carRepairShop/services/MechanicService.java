package com.first.carRepairShop.services;

import com.first.carRepairShop.dto.MechanicDto;
import com.first.carRepairShop.dto.MechanicRegister;
import com.first.carRepairShop.dto.WorkLogDto;
import com.first.carRepairShop.entity.WorkLog;

import java.util.List;

public interface MechanicService {
    MechanicRegister addMechanic(MechanicRegister mechanicRegister);

    void deleteMechanic(Integer mechanicId);

    MechanicDto updateMechanic(MechanicDto mechanicDto);

    List<MechanicDto> getAllMechanic();
    List<WorkLogDto> getAllWorkLog(Integer mechanicId);

}
