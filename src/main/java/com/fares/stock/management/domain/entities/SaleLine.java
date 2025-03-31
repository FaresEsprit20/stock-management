package com.fares.stock.management.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;


@Entity
@Table(name = "sale_line")
public class SaleLine extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sales sale;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "company_id")
    private Integer companyId;

    public SaleLine() {}

    public SaleLine(Sales sale, Product product, BigDecimal quantity, BigDecimal unitPrice, Integer companyId) {
        this.sale = sale;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.companyId = companyId;
    }

    public Sales getSale() {
        return sale;
    }

    public void setSale(Sales sale) {
        this.sale = sale;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SaleLine saleLine = (SaleLine) o;
        return Objects.equals(sale, saleLine.sale) && Objects.equals(product, saleLine.product) && Objects.equals(quantity, saleLine.quantity) && Objects.equals(unitPrice, saleLine.unitPrice) && Objects.equals(companyId, saleLine.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sale, product, quantity, unitPrice, companyId);
    }

    @Override
    public String toString() {
        return "SaleLine{" +
                "sale=" + sale +
                ", product=" + product +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", companyId=" + companyId +
                '}';
    }


}