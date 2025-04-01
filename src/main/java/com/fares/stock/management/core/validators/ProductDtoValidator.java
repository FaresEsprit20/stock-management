package com.fares.stock.management.core.validators;

import com.fares.stock.management.core.constants.Constants;
import com.fares.stock.management.domain.dto.product.ProductDto;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ProductDtoValidator {

    public static List<String> validate(ProductDto productDto) {
        List<String> errors = new ArrayList<>();

        if (productDto == null) {
            errors.add("Code Product field is required");
            errors.add("Designation field is required");
            errors.add("Unit Price field is required");
            errors.add("Amount TVA field is required");
            errors.add("Unit Price TTC field is required");
            errors.add("Photo URL field is required");
            errors.add("ID Enterprise field is required");
            return errors;
        }

        // Code Product Validation
        if (!StringUtils.hasLength(productDto.getCodeProduct()) ||
                productDto.getCodeProduct().length() < Constants.MIN_CODE_LENGTH ||
                productDto.getCodeProduct().length() > Constants.MAX_CODE_LENGTH ||
                !Pattern.matches(Constants.CODE_REGEX, productDto.getCodeProduct())) {
            errors.add("Code Product is invalid: it must be between " + Constants.MIN_CODE_LENGTH + " and " + Constants.MAX_CODE_LENGTH + " characters and match the regex " + Constants.CODE_REGEX);
        }

        // Designation Validation
        if (!StringUtils.hasLength(productDto.getDesignation()) ||
                productDto.getDesignation().length() < Constants.MIN_DESIGNATION_LENGTH ||
                productDto.getDesignation().length() > Constants.MAX_DESIGNATION_LENGTH) {
            errors.add("Designation is invalid: it must be between " + Constants.MIN_DESIGNATION_LENGTH + " and " + Constants.MAX_DESIGNATION_LENGTH + " characters.");
        }

        // Unit Price Validation (should be greater than zero)
        if (productDto.getUnitPriceHt() == null || productDto.getUnitPriceHt().compareTo(Constants.MIN_UNIT_PRICE) <= 0) {
            errors.add("Unit Price HT is invalid: it must be greater than zero.");
        }

        // Amount TVA Validation (should be greater than or equal to zero)
        if (productDto.getAmountTva() == null || productDto.getAmountTva().compareTo(BigDecimal.ZERO) < 0) {
            errors.add("Amount TVA is invalid: it must be greater than or equal to zero.");
        }

        // Unit Price TTC Validation (should be greater than or equal to Unit Price HT)
// Unit Price TTC Validation (should be greater than or equal to Unit Price HT)
        if (productDto.getUnitPriceTtc() == null || productDto.getUnitPriceTtc().compareTo(productDto.getUnitPriceHt()) < 0) {
            errors.add("Unit Price TTC is invalid: it must be greater than or equal to Unit Price HT.");
        }

        // Photo URL Validation (if provided)
        if (StringUtils.hasLength(productDto.getPhoto()) && !Pattern.matches(Constants.PHOTO_URL_REGEX, productDto.getPhoto())) {
            errors.add("Photo URL is invalid: it must match the valid URL format.");
        }

        // ID Enterprise Validation (not null)
        if (productDto.getIdEnterprise() == null) {
            errors.add("ID Enterprise is required.");
        }

        return errors;
    }




}
