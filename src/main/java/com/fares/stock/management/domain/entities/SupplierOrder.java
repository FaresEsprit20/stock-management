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

    public SupplierOrder() {}

    public SupplierOrder(String code, Instant order_date, OrderStatus order_status, Integer idEnterprise, Supplier supplier, List<SupplierOrderLine> supplierOrderLines) {
        this.code = code;
        this.order_date = order_date;
        this.order_status = order_status;
        this.idEnterprise = idEnterprise;
        this.supplier = supplier;
        this.supplierOrderLines = supplierOrderLines;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Instant order_date) {
        this.order_date = order_date;
    }

    public OrderStatus getOrder_status() {
        return order_status;
    }

    public void setOrder_status(OrderStatus order_status) {
        this.order_status = order_status;
    }

    public Integer getIdEnterprise() {
        return idEnterprise;
    }

    public void setIdEnterprise(Integer idEnterprise) {
        this.idEnterprise = idEnterprise;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<SupplierOrderLine> getSupplierOrderLines() {
        return supplierOrderLines;
    }

    public void setSupplierOrderLines(List<SupplierOrderLine> supplierOrderLines) {
        this.supplierOrderLines = supplierOrderLines;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SupplierOrder that = (SupplierOrder) o;
        return Objects.equals(code, that.code) && Objects.equals(order_date, that.order_date) && order_status == that.order_status && Objects.equals(idEnterprise, that.idEnterprise) && Objects.equals(supplier, that.supplier) && Objects.equals(supplierOrderLines, that.supplierOrderLines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), code, order_date, order_status, idEnterprise, supplier, supplierOrderLines);
    }

    @Override
    public String toString() {
        return "SupplierOrder{" +
                "code='" + code + '\'' +
                ", order_date=" + order_date +
                ", order_status=" + order_status +
                ", idEnterprise=" + idEnterprise +
                ", supplier=" + supplier +
                ", supplierOrderLines=" + supplierOrderLines +
                '}';
    }


}
