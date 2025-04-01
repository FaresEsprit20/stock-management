package com.fares.stock.management.core.validators;

import com.fares.stock.management.core.constants.Constants;
import com.fares.stock.management.domain.entities.Product;
import com.fares.stock.management.domain.entities.Sales;

import java.math.BigDecimal;

public class SaleLineDtoValidator {

    // Validate quantity (should be greater than or equal to zero)
    public static boolean validateQuantity(BigDecimal quantity) {
        return quantity != null && quantity.compareTo(Constants.MIN_QUANTITY) >= 0;
    }

    // Validate unit price (should be greater than or equal to zero)
    public static boolean validateUnitPrice(BigDecimal unitPrice) {
        return unitPrice != null && unitPrice.compareTo(Constants.MIN_UNIT_PRICE) >= 0;
    }

    // Validate companyId (if provided, it must match the company ID regex)
    public static boolean validateCompanyId(Integer companyId) {
        if (companyId == null) {
            return false; // Company ID must be provided
        }
        return companyId.toString().matches(Constants.COMPANY_ID_REGEX);
    }

    // Validate sale (should not be null)
    public static boolean validateSale(Sales sale) {
        return sale != null;
    }

    // Validate product (should not be null)
    public static boolean validateProduct(Product product) {
        return product != null;
    }
}
