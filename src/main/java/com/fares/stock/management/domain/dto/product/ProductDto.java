package com.fares.stock.management.domain.dto.product;

import com.fares.stock.management.domain.entities.Product;
import com.fares.stock.management.domain.dto.category.CategoryDto;
import java.math.BigDecimal;


public class ProductDto {

    private Integer id;
    private String codeProduct;
    private String designation;
    private BigDecimal unitPriceHt;
    private BigDecimal amountTva;
    private BigDecimal unitPriceTtc;
    private String photo;
    private Integer idEnterprise;
    private CategoryDto category;

    // No-args constructor
    public ProductDto() {
    }

    // All-args constructor
    public ProductDto(Integer id, String codeProduct, String designation, BigDecimal unitPriceHt,
                      BigDecimal amountTva, BigDecimal unitPriceTtc, String photo,
                      Integer idEnterprise, CategoryDto category) {
        this.id = id;
        this.codeProduct = codeProduct;
        this.designation = designation;
        this.unitPriceHt = unitPriceHt;
        this.amountTva = amountTva;
        this.unitPriceTtc = unitPriceTtc;
        this.photo = photo;
        this.idEnterprise = idEnterprise;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Getters and Setters
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

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    // Conversion methods
    public static ProductDto fromEntity(Product product) {
        if (product == null) {
            return null;
        }

        return new ProductDto(
                product.getId(),
                product.getCodeProduct(),
                product.getDesignation(),
                product.getUnitPriceHt(),
                product.getAmountTva(),
                product.getUnitPriceTtc(),
                product.getPhoto(),
                product.getIdEnterprise(),
                CategoryDto.fromEntity(product.getCategory())
        );
    }

    public static Product toEntity(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }

        Product product = new Product();
        product.setId(productDto.getId());
        product.setCodeProduct(productDto.getCodeProduct());
        product.setDesignation(productDto.getDesignation());
        product.setUnitPriceHt(productDto.getUnitPriceHt());
        product.setAmountTva(productDto.getAmountTva());
        product.setUnitPriceTtc(productDto.getUnitPriceTtc());
        product.setPhoto(productDto.getPhoto());
        product.setIdEnterprise(productDto.getIdEnterprise());
        product.setCategory(CategoryDto.toEntity(productDto.getCategory()));
        return product;
    }



}
