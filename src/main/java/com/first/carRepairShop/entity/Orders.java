package com.first.carRepairShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    @Column(unique = true, nullable = false)
    private String orderNumber;

    private String supplier; // Supplier name

    private LocalDateTime orderDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status; // Example: PENDING, SHIPPED, DELIVERED, CANCELED

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne // OR just store Integer createdByUserId
    private Employee createdBy;
    @ManyToOne
    private Employee receivedBy;
    private LocalDateTime lastUpdate;
    private String receivingComment;
    private LocalDateTime receivedDate;
    @PrePersist
    public void prePersist() {
        this.orderDate = LocalDateTime.now(); // Set orders date at creation
        if (this.status == null) {
            this.status = OrderStatus.PENDING;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdate = LocalDateTime.now();
    }

}
