package com.fares.stock.management.domain.entities;

import com.fares.stock.management.domain.entities.enums.StockMvtSource;
import com.fares.stock.management.domain.entities.enums.StockMvtType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;


@Entity
@Table(name = "stock_movement")
public class StockMovement extends AbstractEntity {

    @Column(name = "movement_date")
    private Instant movementDate;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Product product;

    @Column(name = "stock_mvt_type")
    @Enumerated(EnumType.STRING)
    private StockMvtType stockMvtType;

    @Column(name = "movement_source")
    @Enumerated(EnumType.STRING)
    private StockMvtSource movementSource;

    @Column(name = "company_id")
    private Integer companyId;

    public StockMovement() {}

    public StockMovement(Instant movementDate, BigDecimal quantity, Product product, StockMvtType stockMvtType, StockMvtSource movementSource, Integer companyId) {
        this.movementDate = movementDate;
        this.quantity = quantity;
        this.product = product;
        this.stockMvtType = stockMvtType;
        this.movementSource = movementSource;
        this.companyId = companyId;
    }

    public Instant getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(Instant movementDate) {
        this.movementDate = movementDate;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public StockMvtType getStockMvtType() {
        return stockMvtType;
    }

    public void setStockMvtType(StockMvtType stockMvtType) {
        this.stockMvtType = stockMvtType;
    }

    public StockMvtSource getMovementSource() {
        return movementSource;
    }

    public void setMovementSource(StockMvtSource movementSource) {
        this.movementSource = movementSource;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }


}