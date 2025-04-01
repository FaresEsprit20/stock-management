package com.fares.stock.management.core.validators;

import com.fares.stock.management.core.constants.Constants;

import java.math.BigDecimal;

public class ProductDtoValidator {

    // Validate codeProduct
    public static boolean validateCodeProduct(String codeProduct) {
        if (codeProduct == null || codeProduct.isEmpty()) {
            return false;
        }
        return codeProduct.length() >= Constants.MIN_CODE_LENGTH
                && codeProduct.length() <= Constants.MAX_CODE_LENGTH
                && codeProduct.matches(Constants.CODE_REGEX);
    }

    // Validate designation
    public static boolean validateDesignation(String designation) {
        if (designation == null || designation.isEmpty()) {
            return false;
        }
        return designation.length() >= Constants.MIN_DESIGNATION_LENGTH
                && designation.length() <= Constants.MAX_DESIGNATION_LENGTH;
    }

    // Validate unit price (should be greater than zero)
    public static boolean validateUnitPrice(BigDecimal unitPrice) {
        if (unitPrice == null) {
            return false;
        }
        return unitPrice.compareTo(Constants.MIN_UNIT_PRICE) > 0;
    }

    // Validate amountTva (should be greater than or equal to zero)
    public static boolean validateAmountTva(BigDecimal amountTva) {
        return amountTva != null && amountTva.compareTo(BigDecimal.ZERO) >= 0;
    }

    // Validate unitPriceTtc (should be greater than or equal to unitPriceHt)
    public static boolean validateUnitPriceTtc(BigDecimal unitPriceTtc, BigDecimal unitPriceHt) {
        return unitPriceTtc != null && unitPriceTtc.compareTo(unitPriceHt) >= 0;
    }

    // Validate photo (if provided, it must match the URL regex)
    public static boolean validatePhoto(String photo) {
        if (photo == null || photo.isEmpty()) {
            return true; // Optional field, skip validation if not provided
        }
        return photo.matches(Constants.PHOTO_URL_REGEX);
    }

    // Validate idEnterprise (not null)
    public static boolean validateIdEnterprise(Integer idEnterprise) {
        return idEnterprise != null;
    }
    
}
