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
}
