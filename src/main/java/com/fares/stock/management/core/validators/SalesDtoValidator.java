package com.fares.stock.management.core.validators;

import com.fares.stock.management.core.constants.FieldsValidation;
import com.fares.stock.management.domain.dto.sale_line.SaleLineDto;
import com.fares.stock.management.domain.dto.sales.SalesDto;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class SalesDtoValidator {

    // Validate code (should not be null and meet length/format requirements)
    public static List<String> validateCode(String code) {
        List<String> errors = new ArrayList<>();
        if (!StringUtils.hasLength(code) ||
                code.length() < FieldsValidation.MIN_CODE_LENGTH ||
                code.length() > FieldsValidation.MAX_CODE_LENGTH ||
                !code.matches(FieldsValidation.CODE_REGEX)) {
            errors.add("Code is invalid: it must be between " + FieldsValidation.MIN_CODE_LENGTH + " and " + FieldsValidation.MAX_CODE_LENGTH + " characters and match the format " + FieldsValidation.CODE_REGEX);
        }
        return errors;
    }

    // Validate sale date (should not be null)
    public static List<String> validateSaleDate(Instant saleDate) {
        List<String> errors = new ArrayList<>();
        if (saleDate == null) {
            errors.add("Sale Date is required.");
        }
        return errors;
    }

    // Validate comment (optional, but if present, should meet length constraints)
    public static List<String> validateComment(String comment) {
        List<String> errors = new ArrayList<>();
        if (comment != null && (comment.length() < FieldsValidation.MIN_COMMENT_LENGTH || comment.length() > FieldsValidation.MAX_COMMENT_LENGTH)) {
            errors.add("Comment is invalid: it must be between " + FieldsValidation.MIN_COMMENT_LENGTH + " and " + FieldsValidation.MAX_COMMENT_LENGTH + " characters.");
        }
        return errors;
    }

    // Validate ID Enterprise (should not be null)
    public static List<String> validateIdEnterprise(Integer idEnterprise) {
        List<String> errors = new ArrayList<>();
        if (idEnterprise == null) {
            errors.add("ID Enterprise is required.");
        }
        return errors;
    }

    // Validate Sale Lines (should not be null and must have at least one entry)
    public static List<String> validateSaleLines(List<SaleLineDto> saleLines) {
        List<String> errors = new ArrayList<>();
        if (saleLines == null || saleLines.isEmpty()) {
            errors.add("At least one Sale Line is required.");
        }
        return errors;
    }

    // Validate entire SalesDto
    public static List<String> validate(SalesDto salesDto) {
        List<String> errors = new ArrayList<>();

        if (salesDto == null) {
            errors.add("SalesDto is required.");
            return errors;
        }

        // Validate each field
        errors.addAll(validateCode(salesDto.getCode()));
        errors.addAll(validateSaleDate(salesDto.getSaleDate()));
        errors.addAll(validateComment(salesDto.getComment()));
        errors.addAll(validateIdEnterprise(salesDto.getIdEnterprise()));
        errors.addAll(validateSaleLines(salesDto.getSaleLines()));

        return errors;
    }


}
