package com.fares.stock.management.domain.dto.customer_order_line;

import com.fares.stock.management.domain.entities.CustomerOrder;
import com.fares.stock.management.domain.entities.Product;
import com.fares.stock.management.domain.entities.CustomerOrderLine;
import java.math.BigDecimal;

public class CustomerOrderLineDto {

    private Product product;
    private CustomerOrder customerOrder;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private Integer companyId;

    // No-args constructor
    public CustomerOrderLineDto() {}

    // All-args constructor
    public CustomerOrderLineDto(Product product, CustomerOrder customerOrder, BigDecimal quantity, BigDecimal unitPrice, Integer companyId) {
        this.product = product;
        this.customerOrder = customerOrder;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.companyId = companyId;
    }

    // Getters
    public Product getProduct() { return product; }
    public CustomerOrder getCustomerOrder() { return customerOrder; }
    public BigDecimal getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public Integer getCompanyId() { return companyId; }

    // Setters
    public void setProduct(Product product) { this.product = product; }
    public void setCustomerOrder(CustomerOrder customerOrder) { this.customerOrder = customerOrder; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public void setCompanyId(Integer companyId) { this.companyId = companyId; }

    // Builder pattern
    public static CustomerOrderLineDtoBuilder builder() { return new CustomerOrderLineDtoBuilder(); }

    public static class CustomerOrderLineDtoBuilder {
        private Product product;
        private CustomerOrder customerOrder;
        private BigDecimal quantity;
        private BigDecimal unitPrice;
        private Integer companyId;

        public CustomerOrderLineDtoBuilder product(Product product) { this.product = product; return this; }
        public CustomerOrderLineDtoBuilder customerOrder(CustomerOrder customerOrder) { this.customerOrder = customerOrder; return this; }
        public CustomerOrderLineDtoBuilder quantity(BigDecimal quantity) { this.quantity = quantity; return this; }
        public CustomerOrderLineDtoBuilder unitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; return this; }
        public CustomerOrderLineDtoBuilder companyId(Integer companyId) { this.companyId = companyId; return this; }

        public CustomerOrderLineDto build() {
            return new CustomerOrderLineDto(product, customerOrder, quantity, unitPrice, companyId);
        }
    }

    // Conversion methods
    public static CustomerOrderLineDto fromEntity(CustomerOrderLine entity) {
        if (entity == null) {
            return null;
        }
        return CustomerOrderLineDto.builder()
                .product(entity.getProduct())
                .customerOrder(entity.getCustomerOrder())
                .quantity(entity.getQuantity())
                .unitPrice(entity.getUnitPrice())
                .companyId(entity.getCompanyId())
                .build();
    }

    public static CustomerOrderLine toEntity(CustomerOrderLineDto dto) {
        if (dto == null) {
            return null;
        }
        CustomerOrderLine entity = new CustomerOrderLine();
        entity.setProduct(dto.getProduct());
        entity.setCustomerOrder(dto.getCustomerOrder());
        entity.setQuantity(dto.getQuantity());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setCompanyId(dto.getCompanyId());
        return entity;
    }


}