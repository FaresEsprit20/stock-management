package com.fares.stock.management.domain.dto.roles;

import com.fares.stock.management.domain.entities.Roles;
import com.fares.stock.management.domain.entities.User;

public class RolesDto {

    private String roleName;
    private User user;

    // No-args constructor
    public RolesDto() {
    }

    // All-args constructor
    public RolesDto(String roleName, User user) {
        this.roleName = roleName;
        this.user = user;
    }

    // Getters
    public String getRoleName() {
        return roleName;
    }

    public User getUser() {
        return user;
    }

    // Setters
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Static conversion methods
    public static RolesDto fromEntity(Roles roles) {
        if (roles == null) {
            return null;
        }
        return new RolesDto(
                roles.getRoleName(),
                roles.getUser()
        );
    }

    public static Roles toEntity(RolesDto rolesDto) {
        if (rolesDto == null) {
            return null;
        }
        Roles roles = new Roles();
        roles.setRoleName(rolesDto.getRoleName());
        roles.setUser(rolesDto.getUser());
        return roles;
    }
}

