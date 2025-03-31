package com.fares.stock.management.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "product")
public class Product extends AbstractEntity {

    @Column(name = "code")
    private String codeProduct;

    @Column(name = "designation")
    private String designation;

    @Column(name = "unit_price_ht")
    private BigDecimal unitPriceHt;

    @Column(name = "amount_tva")
    private BigDecimal amountTva;

    @Column(name = "unit_price_ttc")
    private BigDecimal unitPriceTtc;

    @Column(name = "photo")
    private String photo;

    @Column(name = "entreprise_id")
    private Integer idEnterprise;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<SaleLine> saleLines;

    @OneToMany(mappedBy = "product")
    private List<CustomerOrderLine> customerOrderLines;

    @OneToMany(mappedBy = "product")
    private List<SupplierOrderLine> supplierOrderLines;

    @OneToMany(mappedBy = "product")
    private List<StockMovement> stockMovements;


}

