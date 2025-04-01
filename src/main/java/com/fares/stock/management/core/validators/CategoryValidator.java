package com.fares.stock.management.core.validators;


import com.fares.stock.management.core.constants.Constants;
import com.fares.stock.management.domain.dto.category.CategoryDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CategoryValidator {

    public static List<String> validate(CategoryDto categoryDto) {
        List<String> errors = new ArrayList<>();

        if (categoryDto == null) {
            errors.add("Code field is required");
            errors.add("Designation field is required");
            errors.add("Enterprise ID field is required");
            return errors;
        }

        // Code validation
        if (!StringUtils.hasLength(categoryDto.getCode())) {
            errors.add("Code field is required");
        } else if (categoryDto.getCode().length() < Constants.MIN_CODE_LENGTH) {
            errors.add("Code must be at least " + Constants.MIN_CODE_LENGTH + " characters");
        } else if (categoryDto.getCode().length() > Constants.MAX_CODE_LENGTH) {
            errors.add("Code must be no more than " + Constants.MAX_CODE_LENGTH + " characters");
        } else if (!categoryDto.getCode().matches(Constants.CODE_REGEX)) {
            errors.add("Code contains invalid characters (only alphanumeric and dashes allowed)");
        }

        // Designation validation
        if (!StringUtils.hasLength(categoryDto.getDesignation())) {
            errors.add("Designation field is required");
        } else if (categoryDto.getDesignation().length() < Constants.MIN_DESIGNATION_LENGTH) {
            errors.add("Designation must be at least " + Constants.MIN_DESIGNATION_LENGTH + " characters");
        } else if (categoryDto.getDesignation().length() > Constants.MAX_DESIGNATION_LENGTH) {
            errors.add("Designation must be no more than " + Constants.MAX_DESIGNATION_LENGTH + " characters");
        } else if (!categoryDto.getDesignation().matches(Constants.DESIGNATION_REGEX)) {
            errors.add("Designation contains invalid characters (only alphanumeric, spaces, commas, dots, and hyphens allowed)");
        }

        // Enterprise ID validation
        if (categoryDto.getIdEnterprise() == null) {
            errors.add("Enterprise ID field is required");
        }

        return errors;
    }
}

