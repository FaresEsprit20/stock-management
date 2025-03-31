package com.fares.stock.management.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


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


    public Product() {}

    public Product(String codeProduct, String designation, BigDecimal unitPriceHt, BigDecimal amountTva, BigDecimal unitPriceTtc, String photo, Integer idEnterprise, Category category, List<SaleLine> saleLines, List<CustomerOrderLine> customerOrderLines, List<SupplierOrderLine> supplierOrderLines, List<StockMovement> stockMovements) {
        this.codeProduct = codeProduct;
        this.designation = designation;
        this.unitPriceHt = unitPriceHt;
        this.amountTva = amountTva;
        this.unitPriceTtc = unitPriceTtc;
        this.photo = photo;
        this.idEnterprise = idEnterprise;
        this.category = category;
        this.saleLines = saleLines;
        this.customerOrderLines = customerOrderLines;
        this.supplierOrderLines = supplierOrderLines;
        this.stockMovements = stockMovements;
    }

    public String getCodeProduct() {
        return codeProduct;
    }

    public void setCodeProduct(String codeProduct) {
        this.codeProduct = codeProduct;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public BigDecimal getUnitPriceHt() {
        return unitPriceHt;
    }

    public void setUnitPriceHt(BigDecimal unitPriceHt) {
        this.unitPriceHt = unitPriceHt;
    }

    public BigDecimal getAmountTva() {
        return amountTva;
    }

    public void setAmountTva(BigDecimal amountTva) {
        this.amountTva = amountTva;
    }

    public BigDecimal getUnitPriceTtc() {
        return unitPriceTtc;
    }

    public void setUnitPriceTtc(BigDecimal unitPriceTtc) {
        this.unitPriceTtc = unitPriceTtc;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getIdEnterprise() {
        return idEnterprise;
    }

    public void setIdEnterprise(Integer idEnterprise) {
        this.idEnterprise = idEnterprise;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<SaleLine> getSaleLines() {
        return saleLines;
    }

    public void setSaleLines(List<SaleLine> saleLines) {
        this.saleLines = saleLines;
    }

    public List<CustomerOrderLine> getCustomerOrderLines() {
        return customerOrderLines;
    }

    public void setCustomerOrderLines(List<CustomerOrderLine> customerOrderLines) {
        this.customerOrderLines = customerOrderLines;
    }

    public List<SupplierOrderLine> getSupplierOrderLines() {
        return supplierOrderLines;
    }

    public void setSupplierOrderLines(List<SupplierOrderLine> supplierOrderLines) {
        this.supplierOrderLines = supplierOrderLines;
    }

    public List<StockMovement> getStockMovements() {
        return stockMovements;
    }

    public void setStockMovements(List<StockMovement> stockMovements) {
        this.stockMovements = stockMovements;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Product product = (Product) o;
        return Objects.equals(codeProduct, product.codeProduct) && Objects.equals(designation, product.designation) && Objects.equals(unitPriceHt, product.unitPriceHt) && Objects.equals(amountTva, product.amountTva) && Objects.equals(unitPriceTtc, product.unitPriceTtc) && Objects.equals(photo, product.photo) && Objects.equals(idEnterprise, product.idEnterprise) && Objects.equals(category, product.category) && Objects.equals(saleLines, product.saleLines) && Objects.equals(customerOrderLines, product.customerOrderLines) && Objects.equals(supplierOrderLines, product.supplierOrderLines) && Objects.equals(stockMovements, product.stockMovements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), codeProduct, designation, unitPriceHt, amountTva, unitPriceTtc, photo, idEnterprise, category, saleLines, customerOrderLines, supplierOrderLines, stockMovements);
    }



}

