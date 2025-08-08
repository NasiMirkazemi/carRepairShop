package com.first.carRepairShop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceivedOrderRequest {
    private Integer receivedBy;
    private String comment;

}
