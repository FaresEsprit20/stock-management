package com.fares.stock.management.core.validators;

import com.fares.stock.management.core.constants.FieldsValidation;
import com.fares.stock.management.domain.dto.address.AddressDto;
import com.fares.stock.management.domain.dto.customer.CustomerDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CustomerValidator {

    public static List<String> validate(CustomerDto customerDto) {
        List<String> errors = new ArrayList<>();

        if (customerDto == null) {
            errors.add("First Name field is required");
            errors.add("Last Name field is required");
            errors.add("Email field is required");
            errors.add("Phone Number field is required");
            errors.add("Address field is required");
            return errors;
        }

        // First Name Validation
        if (!StringUtils.hasLength(customerDto.getFirstName()) ||
                customerDto.getFirstName().length() < FieldsValidation.MIN_FIRST_NAME_LENGTH ||
                customerDto.getFirstName().length() > FieldsValidation.MAX_FIRST_NAME_LENGTH ||
                !Pattern.matches(FieldsValidation.FIRST_NAME_REGEX, customerDto.getFirstName())) {
            errors.add("First Name is invalid");
        }

        // Last Name Validation
        if (!StringUtils.hasLength(customerDto.getLastName()) ||
                customerDto.getLastName().length() < FieldsValidation.MIN_LAST_NAME_LENGTH ||
                customerDto.getLastName().length() > FieldsValidation.MAX_LAST_NAME_LENGTH ||
                !Pattern.matches(FieldsValidation.LAST_NAME_REGEX, customerDto.getLastName())) {
            errors.add("Last Name is invalid");
        }

        // Email Validation
        if (!StringUtils.hasLength(customerDto.getEmail()) ||
                !Pattern.matches(FieldsValidation.EMAIL_REGEX, customerDto.getEmail())) {
            errors.add("Email is invalid");
        }

        // Phone Number Validation
        if (!StringUtils.hasLength(customerDto.getNumTel()) ||
                !Pattern.matches(FieldsValidation.PHONE_NUMBER_REGEX, customerDto.getNumTel())) {
            errors.add("Phone Number is invalid");
        }

        // Address Validation (Inherit from AddressValidator)
        if (customerDto.getAddress() != null) {
            errors.addAll(AddressValidator.validate(AddressDto.fromEntity(customerDto.getAddress())));
        } else {
            errors.add("Address is required");
        }

        return errors;
    }
}
