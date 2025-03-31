package com.fares.stock.management.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "supplier")
public class Supplier extends AbstractEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Embedded
    private Address address;

    @Column(name = "photo")
    private String photo;

    @Column(name = "email")
    private String email;

    @Column(name = "numTel")
    private String numTel;

    @Column(name = "enterprise_id")
    private Integer idEnterprise;

    @OneToMany(mappedBy = "supplier")
    private List<SupplierOrder> supplierOrders;

    public Supplier() {}

    public Supplier(String firstName, String lastName, Address address, String photo, String email, String numTel, Integer idEnterprise) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.photo = photo;
        this.email = email;
        this.numTel = numTel;
        this.idEnterprise = idEnterprise;
    }

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public Integer getIdEnterprise() {
        return idEnterprise;
    }

    public void setIdEnterprise(Integer idEnterprise) {
        this.idEnterprise = idEnterprise;
    }

    public List<SupplierOrder> getSupplierOrders() {
        return supplierOrders;
    }

    public void setSupplierOrders(List<SupplierOrder> supplierOrders) {
        this.supplierOrders = supplierOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(firstName, supplier.firstName) && Objects.equals(lastName, supplier.lastName) && Objects.equals(address, supplier.address) && Objects.equals(photo, supplier.photo) && Objects.equals(email, supplier.email) && Objects.equals(numTel, supplier.numTel) && Objects.equals(idEnterprise, supplier.idEnterprise) && Objects.equals(supplierOrders, supplier.supplierOrders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, address, photo, email, numTel, idEnterprise, supplierOrders);
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                ", photo='" + photo + '\'' +
                ", email='" + email + '\'' +
                ", numTel='" + numTel + '\'' +
                ", idEnterprise=" + idEnterprise +
                ", supplierOrders=" + supplierOrders +
                '}';
    }


}

