package com.fares.stock.management.core.validators;

import com.fares.stock.management.core.utils.constants.FieldsValidation;
import com.fares.stock.management.domain.dto.stock_movement.StockMovementDto;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StockMovementDtoValidator {

    public static List<String> validate(StockMovementDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("StockMovementDto is required.");
            return errors;
        }

        // Validate movementDate (should not be null and must match ISO8601 format)
        if (dto.getMovementDate() == null) {
            errors.add("Movement date is required.");
        } else {
            String dateString = dto.getMovementDate().toString();
            if (!Pattern.matches(FieldsValidation.DATE_REGEX, dateString)) {
                errors.add("Movement date is invalid: it must follow ISO8601 format.");
            }
        }

        // Validate quantity (must be between MIN_QUANTITY and MAX_QUANTITY)
        if (dto.getQuantity() == null) {
            errors.add("Quantity is required.");
        } else if (dto.getQuantity().compareTo(FieldsValidation.MIN_QUANTITY) < 0 ||
                dto.getQuantity().compareTo(FieldsValidation.MAX_QUANTITY) > 0) {
            errors.add("Quantity must be between " + FieldsValidation.MIN_QUANTITY + " and " + FieldsValidation.MAX_QUANTITY + ".");
        }

        // Validate companyId (should not be null and must be a positive number)
        if (dto.getCompanyId() == null || dto.getCompanyId() <= 0) {
            errors.add("Company ID is required and must be a positive number.");
        }

        // Validate product (should not be null)
        if (dto.getProduct() == null) {
            errors.add("Product is required.");
        }

        // Validate stock movement type (should not be null)
        if (dto.getStockMvtType() == null) {
            errors.add("Stock movement type is required.");
        }

        // Validate movement source (should not be null)
        if (dto.getMovementSource() == null) {
            errors.add("Movement source is required.");
        }

        return errors;
    }
}
