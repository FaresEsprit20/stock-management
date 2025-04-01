package com.fares.stock.management.domain.dto.supplier_order_line;


import com.fares.stock.management.domain.dto.product.ProductDto;
import com.fares.stock.management.domain.dto.supplier_dto.SupplierOrderDto;
import com.fares.stock.management.domain.entities.SupplierOrderLine;

import java.math.BigDecimal;

public class SupplierOrderLineDto {

    private ProductDto product;
    private SupplierOrderDto supplierOrder;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private Integer companyId;

    // No-args constructor
    public SupplierOrderLineDto() {
    }

    // All-args constructor
    public SupplierOrderLineDto(ProductDto product, SupplierOrderDto supplierOrder,
                                BigDecimal quantity, BigDecimal unitPrice, Integer companyId) {
        this.product = product;
        this.supplierOrder = supplierOrder;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.companyId = companyId;
    }

    // Getters
    public ProductDto getProduct() {
        return product;
    }

    public SupplierOrderDto getSupplierOrder() {
        return supplierOrder;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    // Setters
    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public void setSupplierOrder(SupplierOrderDto supplierOrder) {
        this.supplierOrder = supplierOrder;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    // Static conversion methods
    public static SupplierOrderLineDto fromEntity(SupplierOrderLine supplierOrderLine) {
        if (supplierOrderLine == null) {
            return null;
        }
        return new SupplierOrderLineDto(
                ProductDto.fromEntity(supplierOrderLine.getProduct()),
                SupplierOrderDto.fromEntity(supplierOrderLine.getSupplierOrder()),
                supplierOrderLine.getQuantity(),
                supplierOrderLine.getUnitPrice(),
                supplierOrderLine.getCompanyId()
        );
    }

    public static SupplierOrderLine toEntity(SupplierOrderLineDto supplierOrderLineDto) {
        if (supplierOrderLineDto == null) {
            return null;
        }
        SupplierOrderLine supplierOrderLine = new SupplierOrderLine();
        supplierOrderLine.setProduct(ProductDto.toEntity(supplierOrderLineDto.getProduct()));
        supplierOrderLine.setSupplierOrder(SupplierOrderDto.toEntity(supplierOrderLineDto.getSupplierOrder()));
        supplierOrderLine.setQuantity(supplierOrderLineDto.getQuantity());
        supplierOrderLine.setUnitPrice(supplierOrderLineDto.getUnitPrice());
        supplierOrderLine.setCompanyId(supplierOrderLineDto.getCompanyId());
        return supplierOrderLine;
    }


}
