package com.fares.stock.management.core.validators;

import com.fares.stock.management.core.constants.Constants;
import com.fares.stock.management.domain.dto.supplier_order_line.SupplierOrderLineDto;

import java.util.ArrayList;
import java.util.List;

public class SupplierOrderLineDtoValidator {

    public static List<String> validate(SupplierOrderLineDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("Product is required.");
            errors.add("Supplier Order is required.");
            errors.add("Quantity is required.");
            errors.add("Unit Price is required.");
            errors.add("Company ID is required.");
            return errors;
        }

        // Validate Product (should not be null)
        if (dto.getProduct() == null) {
            errors.add("Product is required.");
        }

        // Validate Supplier Order (should not be null)
        if (dto.getSupplierOrder() == null) {
            errors.add("Supplier Order is required.");
        }

        // Validate Quantity (must be between MIN_QUANTITY and MAX_QUANTITY)
        if (dto.getQuantity() == null) {
            errors.add("Quantity is required.");
        } else if (dto.getQuantity().compareTo(Constants.MIN_QUANTITY) < 0 ||
                dto.getQuantity().compareTo(Constants.MAX_QUANTITY) > 0) {
            errors.add("Quantity must be between " + Constants.MIN_QUANTITY + " and " + Constants.MAX_QUANTITY + ".");
        }

        // Validate Unit Price (must be greater than or equal to MIN_UNIT_PRICE)
        if (dto.getUnitPrice() == null) {
            errors.add("Unit Price is required.");
        } else if (dto.getUnitPrice().compareTo(Constants.MIN_UNIT_PRICE) < 0) {
            errors.add("Unit Price must be greater than or equal to " + Constants.MIN_UNIT_PRICE + ".");
        }

        // Validate Company ID (should not be null and must be a positive number)
        if (dto.getCompanyId() == null || dto.getCompanyId() <= 0) {
            errors.add("Company ID is required and must be a positive number.");
        }
        return errors;
    }


}
