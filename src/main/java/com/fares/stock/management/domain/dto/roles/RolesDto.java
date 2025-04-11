package com.fares.stock.management.domain.dto.roles;

import com.fares.stock.management.domain.dto.user.UserDto;
import com.fares.stock.management.domain.entities.Roles;
import com.fares.stock.management.domain.entities.enums.UserRole;

public class RolesDto {

    private UserRole roleName;
    private UserDto user;

    // No-args constructor
    public RolesDto() {
    }

    // All-args constructor
    public RolesDto(UserRole roleName, UserDto user) {
        this.roleName = roleName;
        this.user = user;
    }

    // Getters
    public UserRole getRoleName() {
        return roleName;
    }

    public UserDto getUser() {
        return user;
    }

    // Setters
    public void setRoleName(UserRole roleName) {
        this.roleName = roleName;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    // Static conversion methods
    public static RolesDto fromEntity(Roles roles) {
        if (roles == null) {
            return null;
        }
        return new RolesDto(
                roles.getRoleName(),
                UserDto.fromEntity(roles.getUser())
        );
    }

    public static Roles toEntity(RolesDto rolesDto) {
        if (rolesDto == null) {
            return null;
        }
        Roles roles = new Roles();
        roles.setRoleName(rolesDto.getRoleName());
        roles.setUser(UserDto.toEntity(rolesDto.getUser()) );
        return roles;
    }
}

