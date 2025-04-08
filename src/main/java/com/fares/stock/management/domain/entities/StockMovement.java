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

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
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


}