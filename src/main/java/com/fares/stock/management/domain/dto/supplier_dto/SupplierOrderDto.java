package com.fares.stock.management.domain.dto.supplier_dto;

import com.fares.stock.management.domain.dto.supplier.SupplierDto;
import com.fares.stock.management.domain.dto.supplier_order_line.SupplierOrderLineDto;
import com.fares.stock.management.domain.entities.SupplierOrder;
import com.fares.stock.management.domain.entities.enums.OrderStatus;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class SupplierOrderDto {

    private Integer id;
    private String code;
    private Instant orderDate;
    private OrderStatus orderStatus;
    private Integer idEnterprise;
    private SupplierDto supplier;
    private List<SupplierOrderLineDto> supplierOrderLines;

    // No-args constructor
    public SupplierOrderDto() {
    }

    // All-args constructor
    public SupplierOrderDto(Integer id, String code, Instant orderDate, OrderStatus orderStatus,
                            Integer idEnterprise, SupplierDto supplier,
                            List<SupplierOrderLineDto> supplierOrderLines) {
        this.id = id;
        this.code = code;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.idEnterprise = idEnterprise;
        this.supplier = supplier;
        this.supplierOrderLines = supplierOrderLines;
    }

    // Getters and Setters


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getIdEnterprise() {
        return idEnterprise;
    }

    public void setIdEnterprise(Integer idEnterprise) {
        this.idEnterprise = idEnterprise;
    }

    public SupplierDto getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDto supplier) {
        this.supplier = supplier;
    }

    public List<SupplierOrderLineDto> getSupplierOrderLines() {
        return supplierOrderLines;
    }

    public void setSupplierOrderLines(List<SupplierOrderLineDto> supplierOrderLines) {
        this.supplierOrderLines = supplierOrderLines;
    }

    // Static conversion methods
    public static SupplierOrderDto fromEntity(SupplierOrder supplierOrder) {
        if (supplierOrder == null) {
            return null;
        }
        return new SupplierOrderDto(
                supplierOrder.getId(),
                supplierOrder.getCode(),
                supplierOrder.getOrder_date(),
                supplierOrder.getOrder_status(),
                supplierOrder.getIdEnterprise(),
                SupplierDto.fromEntity(supplierOrder.getSupplier()),
                supplierOrder.getSupplierOrderLines().stream()
                        .map(SupplierOrderLineDto::fromEntity)
                        .collect(Collectors.toList())
        );
    }

    public static SupplierOrder toEntity(SupplierOrderDto supplierOrderDto) {
        if (supplierOrderDto == null) {
            return null;
        }
        SupplierOrder supplierOrder = new SupplierOrder();
        supplierOrder.setId(supplierOrderDto.getId());
        supplierOrder.setCode(supplierOrderDto.getCode());
        supplierOrder.setOrder_date(supplierOrderDto.getOrderDate());
        supplierOrder.setOrder_status(supplierOrderDto.getOrderStatus());
        supplierOrder.setIdEnterprise(supplierOrderDto.getIdEnterprise());
        supplierOrder.setSupplier(SupplierDto.toEntity(supplierOrderDto.getSupplier()));
        supplierOrder.setSupplierOrderLines(supplierOrderDto.getSupplierOrderLines().stream()
                .map(SupplierOrderLineDto::toEntity)
                .collect(Collectors.toList()));
        return supplierOrder;
    }

    public boolean isCommandeLivree() {
        return OrderStatus.LIVREE.equals(this.orderStatus);
    }


}

