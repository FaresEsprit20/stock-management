package com.fares.stock.management.domain.dto.sale_line;

import com.fares.stock.management.domain.entities.Product;
import com.fares.stock.management.domain.entities.SaleLine;
import com.fares.stock.management.domain.entities.Sales;

import java.math.BigDecimal;

public class SaleLineDto {

    private Sales sale;
    private Product product;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private Integer companyId;

    // No-args constructor
    public SaleLineDto() {
    }

    // All-args constructor
    public SaleLineDto(Sales sale, Product product, BigDecimal quantity,
                       BigDecimal unitPrice, Integer companyId) {
        this.sale = sale;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.companyId = companyId;
    }

    // Getters
    public Sales getSale() {
        return sale;
    }

    public Product getProduct() {
        return product;
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
    public void setSale(Sales sale) {
        this.sale = sale;
    }

    public void setProduct(Product product) {
        this.product = product;
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
    public static SaleLineDto fromEntity(SaleLine saleLine) {
        if (saleLine == null) {
            return null;
        }
        return new SaleLineDto(
                saleLine.getSale(),
                saleLine.getProduct(),
                saleLine.getQuantity(),
                saleLine.getUnitPrice(),
                saleLine.getCompanyId()
        );
    }

    public static SaleLine toEntity(SaleLineDto saleLineDto) {
        if (saleLineDto == null) {
            return null;
        }
        SaleLine saleLine = new SaleLine();
        saleLine.setSale(saleLineDto.getSale());
        saleLine.setProduct(saleLineDto.getProduct());
        saleLine.setQuantity(saleLineDto.getQuantity());
        saleLine.setUnitPrice(saleLineDto.getUnitPrice());
        saleLine.setCompanyId(saleLineDto.getCompanyId());
        return saleLine;
    }


}

