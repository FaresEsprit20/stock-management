package com.fares.stock.management.core.validators;

import com.fares.stock.management.core.constants.Constants;
import com.fares.stock.management.domain.dto.supplier_dto.SupplierOrderDto;
import com.fares.stock.management.domain.dto.supplier.SupplierDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SupplierOrderDtoValidator {

    public static List<String> validate(SupplierOrderDto supplierOrderDto) {
        List<String> errors = new ArrayList<>();

        if (supplierOrderDto == null) {
            errors.add("Code field is required");
            errors.add("Order Date field is required");
            errors.add("Order Status field is required");
            errors.add("ID Enterprise field is required");
            errors.add("Supplier field is required");
            errors.add("Supplier Order Lines field is required");
            return errors;
        }

        // Code Validation
        if (!StringUtils.hasLength(supplierOrderDto.getCode()) ||
                supplierOrderDto.getCode().length() < Constants.MIN_CODE_LENGTH ||
                supplierOrderDto.getCode().length() > Constants.MAX_CODE_LENGTH ||
                !Pattern.matches(Constants.CODE_REGEX, supplierOrderDto.getCode())) {
            errors.add("Code is invalid: it must be between " + Constants.MIN_CODE_LENGTH + " and " +
                    Constants.MAX_CODE_LENGTH + " characters and match the pattern " + Constants.CODE_REGEX);
        }

        // Order Date Validation
        if (supplierOrderDto.getOrderDate() == null) {
            errors.add("Order Date is required.");
        }

        // Order Status Validation
        if (supplierOrderDto.getOrderStatus() == null) {
            errors.add("Order Status is required.");
        }

        // ID Enterprise Validation
        if (supplierOrderDto.getIdEnterprise() == null || supplierOrderDto.getIdEnterprise() <= 0) {
            errors.add("ID Enterprise is required and must be a positive number.");
        }

        // Supplier Validation
        SupplierDto supplier = supplierOrderDto.getSupplier();
        if (supplier == null) {
            errors.add("Supplier is required.");
        } else {
            // Reuse the existing SupplierValidator
            errors.addAll(SupplierValidator.validate(supplier));
        }

        // Supplier Order Lines Validation (at least one is required)
        if (supplierOrderDto.getSupplierOrderLines() == null || supplierOrderDto.getSupplierOrderLines().isEmpty()) {
            errors.add("At least one Supplier Order Line is required.");
        }

        return errors;
    }
}

