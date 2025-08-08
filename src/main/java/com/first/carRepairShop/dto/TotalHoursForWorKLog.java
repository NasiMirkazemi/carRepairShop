package com.first.carRepairShop.dto;

import com.first.carRepairShop.entity.WorkLogStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TotalHoursForWorKLog {
    private Integer workLogId;
    private double totalHours;
    private String workLogStatus;
}
