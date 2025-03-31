package com.fares.stock.management.domain.dto.stock_movement;

import com.fares.stock.management.domain.dto.product.ProductDto;
import com.fares.stock.management.domain.entities.StockMovement;
import com.fares.stock.management.domain.entities.enums.StockMvtSource;
import com.fares.stock.management.domain.entities.enums.StockMvtType;

import java.math.BigDecimal;
import java.time.Instant;

public class StockMovementDto {

    private Instant movementDate;
    private BigDecimal quantity;
    private ProductDto product;
    private StockMvtType stockMvtType;
    private StockMvtSource movementSource;
    private Integer companyId;

    // No-args constructor
    public StockMovementDto() {
    }

    // All-args constructor
    public StockMovementDto(Instant movementDate, BigDecimal quantity, ProductDto product,
                            StockMvtType stockMvtType, StockMvtSource movementSource, Integer companyId) {
        this.movementDate = movementDate;
        this.quantity = quantity;
        this.product = product;
        this.stockMvtType = stockMvtType;
        this.movementSource = movementSource;
        this.companyId = companyId;
    }

    // Getters
    public Instant getMovementDate() {
        return movementDate;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public ProductDto getProduct() {
        return product;
    }

    public StockMvtType getStockMvtType() {
        return stockMvtType;
    }

    public StockMvtSource getMovementSource() {
        return movementSource;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    // Setters
    public void setMovementDate(Instant movementDate) {
        this.movementDate = movementDate;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public void setStockMvtType(StockMvtType stockMvtType) {
        this.stockMvtType = stockMvtType;
    }

    public void setMovementSource(StockMvtSource movementSource) {
        this.movementSource = movementSource;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    // Static conversion methods
    public static StockMovementDto fromEntity(StockMovement stockMovement) {
        if (stockMovement == null) {
            return null;
        }
        ProductDto productDto = stockMovement.getProduct() != null ? ProductDto.fromEntity(stockMovement.getProduct()) : null;
        return new StockMovementDto(
                stockMovement.getMovementDate(),
                stockMovement.getQuantity(),
                productDto,
                stockMovement.getStockMvtType(),
                stockMovement.getMovementSource(),
                stockMovement.getCompanyId()
        );
    }

    public static StockMovement toEntity(StockMovementDto stockMovementDto) {
        if (stockMovementDto == null) {
            return null;
        }
        StockMovement stockMovement = new StockMovement();
        stockMovement.setMovementDate(stockMovementDto.getMovementDate());
        stockMovement.setQuantity(stockMovementDto.getQuantity());
        stockMovement.setProduct(stockMovementDto.getProduct() != null ? ProductDto.toEntity(stockMovementDto.getProduct()) : null);
        stockMovement.setStockMvtType(stockMovementDto.getStockMvtType());
        stockMovement.setMovementSource(stockMovementDto.getMovementSource());
        stockMovement.setCompanyId(stockMovementDto.getCompanyId());
        return stockMovement;
    }


}

