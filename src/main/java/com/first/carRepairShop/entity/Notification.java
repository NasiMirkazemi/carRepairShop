package com.first.carRepairShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
    private String message;
    @Column(nullable = false)
    private boolean delivered;




    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // Lifecycle callback to automatically set the sentAt field when updating the notification
    @PreUpdate
    public void preUpdate() {
        if (this.delivered) {
            this.sentAt = LocalDateTime.now();
        }
    }

}
