package com.fares.stock.management.core.validators;

import com.fares.stock.management.core.utils.constants.FieldsValidation;
import com.fares.stock.management.domain.dto.user.UserDto;
import com.fares.stock.management.domain.dto.enterprise.EnterpriseDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserValidator {

    public static List<String> validate(UserDto userDto) {
        List<String> errors = new ArrayList<>();

        if (userDto == null) {
            errors.add("First Name field is required");
            errors.add("Last Name field is required");
            errors.add("Email field is required");
            errors.add("Birth Date field is required");
            errors.add("Password field is required");
            errors.add("Enterprise field is required");
            errors.add("At least one Role is required");
            return errors;
        }

        // First Name Validation
        if (!StringUtils.hasLength(userDto.getFirstName()) ||
                userDto.getFirstName().length() < FieldsValidation.MIN_FIRST_NAME_LENGTH ||
                userDto.getFirstName().length() > FieldsValidation.MAX_FIRST_NAME_LENGTH ||
                !Pattern.matches(FieldsValidation.FIRST_NAME_REGEX, userDto.getFirstName())) {
            errors.add("First Name is invalid: it must be between " + FieldsValidation.MIN_FIRST_NAME_LENGTH + " and " + FieldsValidation.MAX_FIRST_NAME_LENGTH +
                    " characters and match the pattern " + FieldsValidation.FIRST_NAME_REGEX);
        }

        // Last Name Validation
        if (!StringUtils.hasLength(userDto.getLastName()) ||
                userDto.getLastName().length() < FieldsValidation.MIN_LAST_NAME_LENGTH ||
                userDto.getLastName().length() > FieldsValidation.MAX_LAST_NAME_LENGTH ||
                !Pattern.matches(FieldsValidation.LAST_NAME_REGEX, userDto.getLastName())) {
            errors.add("Last Name is invalid: it must be between " + FieldsValidation.MIN_LAST_NAME_LENGTH + " and " + FieldsValidation.MAX_LAST_NAME_LENGTH +
                    " characters and match the pattern " + FieldsValidation.LAST_NAME_REGEX);
        }

        // Email Validation
        if (!StringUtils.hasLength(userDto.getEmail()) ||
                !Pattern.matches(FieldsValidation.EMAIL_REGEX, userDto.getEmail())) {
            errors.add("Email is invalid: it must be a valid email format.");
        }

        // Birth Date Validation
        if (userDto.getBirthDate() == null) {
            errors.add("Birth Date is required.");
        }

        // Password Validation
        if (!StringUtils.hasLength(userDto.getPassword()) ||
                userDto.getPassword().length() < FieldsValidation.MIN_PASSWORD_LENGTH ||
                userDto.getPassword().length() > FieldsValidation.MAX_PASSWORD_LENGTH ||
                !Pattern.matches(FieldsValidation.PASSWORD_REGEX, userDto.getPassword())) {
            errors.add("Password is invalid: it must be between " + FieldsValidation.MIN_PASSWORD_LENGTH + " and " + FieldsValidation.MAX_PASSWORD_LENGTH +
                    " characters and match the required complexity.");
        }

        // Photo Validation (if provided)
        if (StringUtils.hasLength(userDto.getPhoto()) &&
                !Pattern.matches(FieldsValidation.PHOTO_URL_REGEX, userDto.getPhoto())) {
            errors.add("Photo URL is invalid: it must match the valid URL format.");
        }

        // Enterprise Validation (reuse EnterpriseValidator)
        EnterpriseDto enterprise = userDto.getEnterprise();
        if (enterprise == null) {
            errors.add("Enterprise is required.");
        } else {
            errors.addAll(EnterpriseValidator.validate(enterprise));
        }
        // Roles Validation: Ensure at least one role is provided
        if (userDto.getRoles() == null || userDto.getRoles().isEmpty()) {
            errors.add("At least one Role is required.");
        }
        // Optionally, you can validate each role with a RolesValidator if available.
        return errors;
    }


}

