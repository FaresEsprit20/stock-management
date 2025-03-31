package com.fares.stock.management.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Entity
@Table(name = "roles")
public class Roles extends AbstractEntity {

    @Column(name = "role_name")
    private String roleName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Roles() {}

    public Roles(String roleName) {
        this.roleName = roleName;
    }

    public Roles(String roleName, User user) {
        this.roleName = roleName;
        this.user = user;
    }


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Roles roles = (Roles) o;
        return Objects.equals(roleName, roles.roleName) && Objects.equals(user, roles.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), roleName, user);
    }

    @Override
    public String toString() {
        return "Roles{" +
                "roleName='" + roleName + '\'' +
                ", user=" + user +
                '}';
    }



}