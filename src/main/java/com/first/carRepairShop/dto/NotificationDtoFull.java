package com.first.carRepairShop.dto;

import com.first.carRepairShop.entity.Customer;
import com.first.carRepairShop.entity.NotificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class NotificationDtoFull {
    private Integer notificationId;

    @NotNull(message = "Notification type cannot be null.")
    private NotificationType type;

    @Size(max = 255, message = "Message must be at most 255 characters.")
    private String message; // Store the generated message text

    private LocalDateTime createdAt;

    private LocalDateTime sentAt;

    private boolean delivered;

}
