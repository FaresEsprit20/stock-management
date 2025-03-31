package com.fares.stock.management.domain.dto.user;

import com.fares.stock.management.domain.dto.enterprise.EnterpriseDto;
import com.fares.stock.management.domain.dto.roles.RolesDto;
import com.fares.stock.management.domain.entities.User;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class UserDto {

    private String firstName;
    private String lastName;
    private String email;
    private Instant birthDate;
    private String password;
    private String photo;
    private EnterpriseDto enterprise;
    private List<RolesDto> roles;

    // No-args constructor
    public UserDto() {
    }

    // All-args constructor
    public UserDto(String firstName, String lastName, String email, Instant birthDate,
                   String password, String photo, EnterpriseDto enterprise, List<RolesDto> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.password = password;
        this.photo = photo;
        this.enterprise = enterprise;
        this.roles = roles;
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public EnterpriseDto getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(EnterpriseDto enterprise) {
        this.enterprise = enterprise;
    }

    public List<RolesDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RolesDto> roles) {
        this.roles = roles;
    }

    // Static conversion methods
    public static UserDto fromEntity(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getBirthDate(),
                user.getPassword(),
                user.getPhoto(),
                EnterpriseDto.fromEntity(user.getEnterprise()),
                user.getRoles().stream()
                        .map(RolesDto::fromEntity)
                        .collect(Collectors.toList())
        );
    }

    public static User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setBirthDate(userDto.getBirthDate());
        user.setPassword(userDto.getPassword());
        user.setPhoto(userDto.getPhoto());
        user.setEnterprise(EnterpriseDto.toEntity(userDto.getEnterprise()));
        user.setRoles(userDto.getRoles().stream()
                .map(RolesDto::toEntity)
                .collect(Collectors.toList()));
        return user;
    }
}

