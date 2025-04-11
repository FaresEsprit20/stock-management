package com.fares.stock.management.domain.entities;

import com.fares.stock.management.domain.entities.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
public class Roles extends AbstractEntity {

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private UserRole roleName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}