package com.first.carRepairShop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "role") // 👈 exclude circular field
@ToString(exclude = "role")
public class Permissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer permissionId;

    @Column(nullable = false, unique = true)
    private String name;
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> role = new HashSet<>();
    private String description;
}
