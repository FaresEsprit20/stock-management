package com.fares.stock.management.core.validators;

import com.fares.stock.management.core.utils.constants.FieldsValidation;
import com.fares.stock.management.domain.dto.customer_order.CustomerOrderDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CustomerOrderValidator {

    public static List<String> validate(CustomerOrderDto customerOrderDto) {
        List<String> errors = new ArrayList<>();

        if (customerOrderDto == null) {
            errors.add("Order Code field is required");
            errors.add("Order Date field is required");
            errors.add("Order Status field is required");
            errors.add("Company ID field is required");
            errors.add("Customer field is required");
            errors.add("Order Lines field is required");
            return errors;
        }

        // Order Code Validation
        if (!StringUtils.hasLength(customerOrderDto.getCode()) ||
                customerOrderDto.getCode().length() < FieldsValidation.MIN_CODE_LENGTH ||
                customerOrderDto.getCode().length() > FieldsValidation.MAX_CODE_LENGTH ||
                !Pattern.matches(FieldsValidation.CODE_REGEX, customerOrderDto.getCode())) {
            errors.add("Order Code is invalid");
        }

        // Order Status Validation
        if (customerOrderDto.getOrderStatus() == null) {
            errors.add("Order Status is required");
        }

        // Company ID Validation
        if (customerOrderDto.getCompanyId() == null || customerOrderDto.getCompanyId() < FieldsValidation.MIN_COMPANY_ID) {
            errors.add("Company ID is invalid");
        }

        // Customer Validation (Inherit from CustomerValidator)
        if (customerOrderDto.getCustomer() == null) {
            errors.add("Customer is required");
        } else {
            errors.addAll(CustomerValidator.validate(customerOrderDto.getCustomer()));
        }

        // Order Lines Validation
        if (customerOrderDto.getOrderLines() == null || customerOrderDto.getOrderLines().isEmpty() ||
                customerOrderDto.getOrderLines().size() > FieldsValidation.MAX_ORDER_LINES) {
            errors.add("Order Lines must have between 1 and 100 items");
        }

        return errors;
    }



}

