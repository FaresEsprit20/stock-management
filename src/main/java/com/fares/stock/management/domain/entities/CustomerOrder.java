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

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
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



}