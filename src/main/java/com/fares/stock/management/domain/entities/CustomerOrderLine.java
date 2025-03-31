package com.fares.stock.management.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
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


}