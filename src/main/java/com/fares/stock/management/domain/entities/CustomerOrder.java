package com.fares.stock.management.domain.entities;

import com.fares.stock.management.domain.entities.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "customer_order")
public class CustomerOrder extends AbstractEntity {

    @Column(name = "order_code")
    private String code;

    @Column(name = "order_date")
    private Instant orderDate;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "company_id")
    private Integer companyId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "customerOrder")
    private List<CustomerOrderLine> orderLines;

    public CustomerOrder() {}

    public CustomerOrder(String code, Instant orderDate, OrderStatus orderStatus, Integer companyId, Customer customer) {
        this.code = code;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.companyId = companyId;
        this.customer = customer;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<CustomerOrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<CustomerOrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CustomerOrder that = (CustomerOrder) o;
        return Objects.equals(code, that.code) && Objects.equals(orderDate, that.orderDate) && orderStatus == that.orderStatus && Objects.equals(companyId, that.companyId) && Objects.equals(customer, that.customer) && Objects.equals(orderLines, that.orderLines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), code, orderDate, orderStatus, companyId, customer, orderLines);
    }

    @Override
    public String toString() {
        return "CustomerOrder{" +
                "code='" + code + '\'' +
                ", orderDate=" + orderDate +
                ", orderStatus=" + orderStatus +
                ", companyId=" + companyId +
                ", customer=" + customer +
                ", orderLines=" + orderLines +
                '}';
    }


}