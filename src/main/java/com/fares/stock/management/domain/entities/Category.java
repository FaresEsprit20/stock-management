package com.fares.stock.management.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "category")
public class Category extends AbstractEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "designation")
    private String designation;

    @Column(name = "entreprise_id")
    private Integer idEnterprise;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public Category() {}

    public Category(String code, String designation) {
        this.code = code;
        this.designation = designation;
    }

    public Category(String code, String designation, Integer idEnterprise, List<Product> products) {
        this.code = code;
        this.designation = designation;
        this.idEnterprise = idEnterprise;
        this.products = products;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Integer getIdEnterprise() {
        return idEnterprise;
    }

    public void setIdEnterprise(Integer idEnterprise) {
        this.idEnterprise = idEnterprise;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Category category = (Category) o;
        return Objects.equals(code, category.code) && Objects.equals(designation, category.designation) && Objects.equals(idEnterprise, category.idEnterprise) && Objects.equals(products, category.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), code, designation, idEnterprise, products);
    }

    @Override
    public String toString() {
        return "Category{" +
                "code='" + code + '\'' +
                ", designation='" + designation + '\'' +
                ", idEnterprise=" + idEnterprise +
                ", products=" + products +
                '}';
    }


}