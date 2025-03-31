package com.fares.stock.management.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Objects;


@Entity
@Table(name = "customer_order_line")
public class CustomerOrderLine extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_order_id")
    private CustomerOrder customerOrder;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "company_id")
    private Integer companyId;

    public CustomerOrderLine() {}

    public CustomerOrderLine(Product product, CustomerOrder customerOrder, BigDecimal quantity, BigDecimal unitPrice, Integer companyId) {
        this.product = product;
        this.customerOrder = customerOrder;
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

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
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
        CustomerOrderLine that = (CustomerOrderLine) o;
        return Objects.equals(product, that.product) && Objects.equals(customerOrder, that.customerOrder) && Objects.equals(quantity, that.quantity) && Objects.equals(unitPrice, that.unitPrice) && Objects.equals(companyId, that.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), product, customerOrder, quantity, unitPrice, companyId);
    }

    @Override
    public String toString() {
        return "CustomerOrderLine{" +
                "product=" + product +
                ", customerOrder=" + customerOrder +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", companyId=" + companyId +
                '}';
    }


}