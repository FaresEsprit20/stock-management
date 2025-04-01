package com.fares.stock.management.core.validators;

import com.fares.stock.management.core.constants.FieldsValidation;
import com.fares.stock.management.domain.dto.address.AddressDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AddressValidator {

    public static List<String> validate(AddressDto addressDto) {
        List<String> errors = new ArrayList<>();

        if (addressDto == null) {
            errors.add("Address 1 field is required.");
            errors.add("City field is required.");
            errors.add("Country field is required.");
            errors.add("Zip Code field is required.");
            return errors;
        }

        // Validate Address 1
        if (!StringUtils.hasLength(addressDto.getAddress1())) {
            errors.add("Address 1 field is required.");
        } else {
            if (addressDto.getAddress1().length() < FieldsValidation.MIN_ADDRESS_LENGTH ||
                    addressDto.getAddress1().length() > FieldsValidation.MAX_ADDRESS_LENGTH) {
                errors.add("Address 1 must be between " + FieldsValidation.MIN_ADDRESS_LENGTH +
                        " and " + FieldsValidation.MAX_ADDRESS_LENGTH + " characters.");
            }
            if (!Pattern.matches(FieldsValidation.ADDRESS_CONTENT_REGEX, addressDto.getAddress1())) {
                errors.add("Address 1 contains invalid characters.");
            }
        }

        // Validate City
        if (!StringUtils.hasLength(addressDto.getCity())) {
            errors.add("City field is required.");
        } else {
            if (addressDto.getCity().length() < FieldsValidation.MIN_CITY_LENGTH ||
                    addressDto.getCity().length() > FieldsValidation.MAX_CITY_LENGTH) {
                errors.add("City must be between " + FieldsValidation.MIN_CITY_LENGTH +
                        " and " + FieldsValidation.MAX_CITY_LENGTH + " characters.");
            }
            if (!Pattern.matches(FieldsValidation.CITY_REGEX, addressDto.getCity())) {
                errors.add("City contains invalid characters.");
            }
        }

        // Validate Country
        if (!StringUtils.hasLength(addressDto.getCountry())) {
            errors.add("Country field is required.");
        } else {
            if (addressDto.getCountry().length() < FieldsValidation.MIN_COUNTRY_LENGTH ||
                    addressDto.getCountry().length() > FieldsValidation.MAX_COUNTRY_LENGTH) {
                errors.add("Country must be between " + FieldsValidation.MIN_COUNTRY_LENGTH +
                        " and " + FieldsValidation.MAX_COUNTRY_LENGTH + " characters.");
            }
            if (!Pattern.matches(FieldsValidation.COUNTRY_REGEX, addressDto.getCountry())) {
                errors.add("Country contains invalid characters.");
            }
        }

        // Validate Zip Code (Only Digits)
        if (!StringUtils.hasLength(addressDto.getZipCode())) {
            errors.add("Zip Code field is required.");
        } else {
            if (addressDto.getZipCode().length() != FieldsValidation.MIN_ZIPCODE_LENGTH) {
                errors.add("Zip Code must be exactly " + FieldsValidation.MIN_ZIPCODE_LENGTH + " digits.");
            }
            if (!Pattern.matches(FieldsValidation.ZIP_CODE_REGEX, addressDto.getZipCode())) {
                errors.add("Zip Code must contain only digits.");
            }
        }
        return errors;
    }


}
