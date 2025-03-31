package com.fares.stock.management.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "enterprise")
public class Enterprise extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Embedded
    private Address address;

    @Column(name = "codefiscal")
    private String codeFiscal;

    @Column(name = "photo")
    private String photo;

    @Column(name = "email")
    private String email;

    @Column(name = "numtel")
    private String numTel;

    @Column(name = "website")
    private String webSite;

    @OneToMany(mappedBy = "enterprise")
    private List<User> users;


    public Enterprise() {}

    public Enterprise(String name, String description, Address address, String codeFiscal, String photo, String email, String numTel, String webSite, List<User> users) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.codeFiscal = codeFiscal;
        this.photo = photo;
        this.email = email;
        this.numTel = numTel;
        this.webSite = webSite;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCodeFiscal() {
        return codeFiscal;
    }

    public void setCodeFiscal(String codeFiscal) {
        this.codeFiscal = codeFiscal;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Enterprise that = (Enterprise) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(address, that.address) && Objects.equals(codeFiscal, that.codeFiscal) && Objects.equals(photo, that.photo) && Objects.equals(email, that.email) && Objects.equals(numTel, that.numTel) && Objects.equals(webSite, that.webSite) && Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, address, codeFiscal, photo, email, numTel, webSite, users);
    }

    @Override
    public String toString() {
        return "Enterprise{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address=" + address +
                ", codeFiscal='" + codeFiscal + '\'' +
                ", photo='" + photo + '\'' +
                ", email='" + email + '\'' +
                ", numTel='" + numTel + '\'' +
                ", webSite='" + webSite + '\'' +
                ", users=" + users +
                '}';
    }


}
