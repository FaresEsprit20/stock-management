package com.fares.stock.management.domain.dto.category;


import com.fares.stock.management.domain.entities.Category;


public class CategoryDto {

    private String code;
    private String designation;
    private Integer idEnterprise;


    // All-args constructor
    public CategoryDto(String code, String designation, Integer idEnterprise) {
        this.code = code;
        this.designation = designation;
        this.idEnterprise = idEnterprise;
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Integer getIdEnterprise() {
        return idEnterprise;
    }

    public void setIdEnterprise(Integer idEnterprise) {
        this.idEnterprise = idEnterprise;
    }

    // Conversion methods
    public static CategoryDto fromEntity(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryDto(
                category.getCode(),
                category.getDesignation(),
                category.getIdEnterprise()
        );
    }

    public static Category toEntity(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }
        Category category = new Category();
        category.setCode(categoryDto.getCode());
        category.setDesignation(categoryDto.getDesignation());
        category.setIdEnterprise(categoryDto.getIdEnterprise());
        return category;
    }
}
