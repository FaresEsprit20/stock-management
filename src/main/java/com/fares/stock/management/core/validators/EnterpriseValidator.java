package com.fares.stock.management.core.validators;

import com.fares.stock.management.core.utils.constants.constants.FieldsValidation;
import com.fares.stock.management.domain.dto.enterprise.EnterpriseDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EnterpriseValidator {

    public static List<String> validate(EnterpriseDto enterpriseDto) {
        List<String> errors = new ArrayList<>();

        if (enterpriseDto == null) {
            errors.add("Enterprise is required");
            return errors;
        }

        // Name Validation
        if (StringUtils.isEmpty(enterpriseDto.getName()) ||
                enterpriseDto.getName().length() < FieldsValidation.MIN_NAME_LENGTH ||
                enterpriseDto.getName().length() > FieldsValidation.MAX_NAME_LENGTH) {
            errors.add("Name must be between " + FieldsValidation.MIN_NAME_LENGTH + " and " + FieldsValidation.MAX_NAME_LENGTH + " characters");
        }

        // Fiscal Code Validation
        if (StringUtils.isEmpty(enterpriseDto.getCodeFiscal()) ||
                enterpriseDto.getCodeFiscal().length() < FieldsValidation.MIN_CODE_FISCAL_LENGTH ||
                enterpriseDto.getCodeFiscal().length() > FieldsValidation.MAX_CODE_FISCAL_LENGTH) {
            errors.add("Fiscal Code must be between " + FieldsValidation.MIN_CODE_FISCAL_LENGTH + " and " + FieldsValidation.MAX_CODE_FISCAL_LENGTH + " characters");
        }

        // Email Validation
        if (StringUtils.isEmpty(enterpriseDto.getEmail()) ||
                enterpriseDto.getEmail().length() > FieldsValidation.MAX_EMAIL_LENGTH ||
                !enterpriseDto.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.add("Email must be a valid email and no longer than " + FieldsValidation.MAX_EMAIL_LENGTH + " characters");
        }

        // Phone Number Validation
        if (StringUtils.isEmpty(enterpriseDto.getNumTel()) ||
                enterpriseDto.getNumTel().length() != FieldsValidation.MAX_TEL_LENGTH) {
            errors.add("Telephone number must be " + FieldsValidation.MAX_TEL_LENGTH + " characters");
        }

        // Website Validation
        if (StringUtils.isEmpty(enterpriseDto.getWebSite()) ||
                enterpriseDto.getWebSite().length() > FieldsValidation.MAX_WEBSITE_LENGTH) {
            errors.add("Website URL must not exceed " + FieldsValidation.MAX_WEBSITE_LENGTH + " characters");
        }

        return errors;
    }


}
