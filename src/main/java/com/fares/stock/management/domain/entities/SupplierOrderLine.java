package com.fares.stock.management.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;


@Entity
@Table(name = "supplier_order_line")
public class SupplierOrderLine extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "supplier_order_id")
    private SupplierOrder supplierOrder;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "company_id")
    private Integer companyId;

    public SupplierOrderLine() {}

    public SupplierOrderLine(Product product, SupplierOrder supplierOrder, BigDecimal quantity, BigDecimal unitPrice, Integer companyId) {
        this.product = product;
        this.supplierOrder = supplierOrder;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.companyId = companyId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public SupplierOrder getSupplierOrder() {
        return supplierOrder;
    }

    public void setSupplierOrder(SupplierOrder supplierOrder) {
        this.supplierOrder = supplierOrder;
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
        SupplierOrderLine that = (SupplierOrderLine) o;
        return Objects.equals(product, that.product) && Objects.equals(supplierOrder, that.supplierOrder) && Objects.equals(quantity, that.quantity) && Objects.equals(unitPrice, that.unitPrice) && Objects.equals(companyId, that.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), product, supplierOrder, quantity, unitPrice, companyId);
    }

    @Override
    public String toString() {
        return "SupplierOrderLine{" +
                "product=" + product +
                ", supplierOrder=" + supplierOrder +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", companyId=" + companyId +
                '}';
    }


}
