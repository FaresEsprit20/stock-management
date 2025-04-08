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
@Table(name = "supplier_order")
public class SupplierOrder extends AbstractEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "order_date")
    private Instant order_date;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus order_status;

    @Column(name = "enterprise_id")
    private Integer idEnterprise;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @OneToMany(mappedBy = "supplierOrder")
    private List<SupplierOrderLine> supplierOrderLines;


}
