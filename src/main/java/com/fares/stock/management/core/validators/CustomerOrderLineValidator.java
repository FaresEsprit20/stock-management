package com.fares.stock.management.core.validators;

import com.fares.stock.management.core.utils.constants.constants.FieldsValidation;
import com.fares.stock.management.domain.dto.customer_order_line.CustomerOrderLineDto;

import java.util.ArrayList;
import java.util.List;


public class CustomerOrderLineValidator {

    public static List<String> validate(CustomerOrderLineDto customerOrderLineDto) {

        List<String> errors = new ArrayList<>();

        if (customerOrderLineDto == null) {
            errors.add("Product field is required");
            errors.add("Customer Order field is required");
            errors.add("Quantity field is required");
            errors.add("Unit Price field is required");
            errors.add("Company ID field is required");
            return errors;
        }

        // Product Validation
        if (customerOrderLineDto.getProduct() == null) {
            errors.add("Product is required");
        }

        // Customer Order Validation
        if (customerOrderLineDto.getCustomerOrder() == null) {
            errors.add("Customer Order is required");
        }

        // Quantity Validation
        if (customerOrderLineDto.getQuantity() == null || customerOrderLineDto.getQuantity().compareTo(FieldsValidation.MIN_QUANTITY) < 0 ||
                customerOrderLineDto.getQuantity().compareTo(FieldsValidation.MAX_QUANTITY) > 0) {
            errors.add("Quantity must be between 1 and 10000");
        }

        // Unit Price Validation
        if (customerOrderLineDto.getUnitPrice() == null || customerOrderLineDto.getUnitPrice().compareTo(FieldsValidation.MIN_UNIT_PRICE) < 0) {
            errors.add("Unit Price cannot be negative");
        }

        // Company ID Validation
        if (customerOrderLineDto.getCompanyId() == null || customerOrderLineDto.getCompanyId() < FieldsValidation.MIN_COMPANY_ID) {
            errors.add("Company ID is invalid");
        }
        return errors;
    }


}

