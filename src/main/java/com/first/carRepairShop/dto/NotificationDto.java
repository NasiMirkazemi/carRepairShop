package com.first.carRepairShop.dto;

import com.first.carRepairShop.entity.Customer;
import com.first.carRepairShop.entity.NotificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class NotificationDto {

    private Integer notificationId;
    @NotNull(message = "Notification type is required.")
    private NotificationType type;

    @Size(max = 500, message = "Message must not exceed 500 characters.")
    private String message;

    @NotNull(message = "Created at timestamp is required.")
    @PastOrPresent(message = "Created at timestamp cannot be in the future.")
    private LocalDateTime createdAt;

    @PastOrPresent(message = "Sent at timestamp cannot be in the future.")
    private LocalDateTime sentAt;

}
