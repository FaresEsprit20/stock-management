package com.fares.stock.management.core.validators;

import com.fares.stock.management.core.utils.constants.FieldsValidation;
import com.fares.stock.management.domain.entities.Product;
import com.fares.stock.management.domain.entities.Sales;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SaleLineDtoValidator {

    // Validate quantity (should be greater than or equal to zero)
    public static List<String> validateQuantity(BigDecimal quantity) {
        List<String> errors = new ArrayList<>();
        if (quantity == null || quantity.compareTo(FieldsValidation.MIN_QUANTITY) < 0) {
            errors.add("Quantity is invalid: it must be greater than or equal to " + FieldsValidation.MIN_QUANTITY);
        }
        return errors;
    }

    // Validate unit price (should be greater than or equal to zero)
    public static List<String> validateUnitPrice(BigDecimal unitPrice) {
        List<String> errors = new ArrayList<>();
        if (unitPrice == null || unitPrice.compareTo(FieldsValidation.MIN_UNIT_PRICE) < 0) {
            errors.add("Unit Price is invalid: it must be greater than or equal to " + FieldsValidation.MIN_UNIT_PRICE);
        }
        return errors;
    }

    // Validate companyId (if provided, it must match the company ID regex)
    public static List<String> validateCompanyId(Integer companyId) {
        List<String> errors = new ArrayList<>();
        if (companyId == null) {
            errors.add("Company ID is required.");
        } else if (!companyId.toString().matches(FieldsValidation.COMPANY_ID_REGEX)) {
            errors.add("Company ID is invalid: it must match the format " + FieldsValidation.COMPANY_ID_REGEX);
        }
        return errors;
    }

    // Validate sale (should not be null)
    public static List<String> validateSale(Sales sale) {
        List<String> errors = new ArrayList<>();
        if (sale == null) {
            errors.add("Sale is required.");
        }
        return errors;
    }

    // Validate product (should not be null)
    public static List<String> validateProduct(Product product) {
        List<String> errors = new ArrayList<>();
        if (product == null) {
            errors.add("Product is required.");
        }
        return errors;
    }


}
