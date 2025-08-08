package com.first.carRepairShop.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.first.carRepairShop.annotations.ValidSource;
import com.first.carRepairShop.associations.ItemDetail;
import com.first.carRepairShop.associations.ServiceDetail;
import com.first.carRepairShop.entity.WorkLogSource;
import com.first.carRepairShop.entity.WorkLogStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WorkLogDto {

    private Integer workLogId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String workLogNumber;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate createDate;

    private String workLogStatus;

    private Integer mechanicId;

    @NotNull(message = "Assignment ID is required")
    private Integer assignmentId;

    @Valid
    private List<ServiceDetailDto> performedService = new ArrayList<>();

    @Valid
    private List<ItemDetailDto> usedItem = new ArrayList<>();
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime checkIn;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime checkOut;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime pauseStart;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime pauseEnd;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime cancelTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double totalHours;

    private String description;
   // @ValidSource
    private String workLogSource;


}