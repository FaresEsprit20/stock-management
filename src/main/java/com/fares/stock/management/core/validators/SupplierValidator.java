package com.fares.stock.management.core.validators;

import com.fares.stock.management.core.utils.constants.FieldsValidation;
import com.fares.stock.management.domain.dto.supplier.SupplierDto;
import com.fares.stock.management.domain.dto.address.AddressDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class SupplierValidator {

    public static List<String> validate(SupplierDto supplierDto) {
        List<String> errors = new ArrayList<>();

        if (supplierDto == null) {
            errors.add("First Name field is required");
            errors.add("Last Name field is required");
            errors.add("Email field is required");
            errors.add("Phone Number field is required");
            errors.add("Address field is required");
            errors.add("ID Enterprise field is required");
            return errors;
        }

        // First Name Validation
        if (!StringUtils.hasLength(supplierDto.getFirstName()) ||
                supplierDto.getFirstName().length() < FieldsValidation.MIN_FIRST_NAME_LENGTH ||
                supplierDto.getFirstName().length() > FieldsValidation.MAX_FIRST_NAME_LENGTH ||
                !Pattern.matches(FieldsValidation.FIRST_NAME_REGEX, supplierDto.getFirstName())) {
            errors.add("First Name is invalid: it must be between "
                    + FieldsValidation.MIN_FIRST_NAME_LENGTH + " and " + FieldsValidation.MAX_FIRST_NAME_LENGTH
                    + " characters and match the pattern " + FieldsValidation.FIRST_NAME_REGEX);
        }

        // Last Name Validation
        if (!StringUtils.hasLength(supplierDto.getLastName()) ||
                supplierDto.getLastName().length() < FieldsValidation.MIN_LAST_NAME_LENGTH ||
                supplierDto.getLastName().length() > FieldsValidation.MAX_LAST_NAME_LENGTH ||
                !Pattern.matches(FieldsValidation.LAST_NAME_REGEX, supplierDto.getLastName())) {
            errors.add("Last Name is invalid: it must be between "
                    + FieldsValidation.MIN_LAST_NAME_LENGTH + " and " + FieldsValidation.MAX_LAST_NAME_LENGTH
                    + " characters and match the pattern " + FieldsValidation.LAST_NAME_REGEX);
        }

        // Email Validation
        if (!StringUtils.hasLength(supplierDto.getEmail()) ||
                !Pattern.matches(FieldsValidation.EMAIL_REGEX, supplierDto.getEmail())) {
            errors.add("Email is invalid: it must be a valid email format.");
        }

        // Phone Number Validation
        if (!StringUtils.hasLength(supplierDto.getNumTel()) ||
                !Pattern.matches(FieldsValidation.PHONE_NUMBER_REGEX, supplierDto.getNumTel())) {
            errors.add("Phone Number is invalid: it must match the format " + FieldsValidation.PHONE_NUMBER_REGEX);
        }

        // Address Validation using AddressValidator
        if (supplierDto.getAddress() != null) {
            // Convert Address to AddressDto before validating
            errors.addAll(AddressValidator.validate(AddressDto.fromEntity(supplierDto.getAddress())));
        } else {
            errors.add("Address is required.");
        }

        // ID Enterprise Validation
        if (supplierDto.getIdEnterprise() == null || supplierDto.getIdEnterprise() <= 0) {
            errors.add("ID Enterprise is required and must be a positive number.");
        }

        return errors;
    }



}

