package com.first.carRepairShop.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkLogNotificationMessage {
    private String type;
    private Integer assignmentId;
    private Integer workLogId;
    private String message;
    private LocalDateTime localDateTime;
    private Map<String, Object> metadata;

}
