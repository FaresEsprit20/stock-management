package com.fares.stock.management.core.validators;

import com.fares.stock.management.domain.dto.address.AddressDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AddressValidator {

    public static List<String> validate(AddressDto addressDto) {
        List<String> errors = new ArrayList<>();

        if (addressDto == null) {
            errors.add("Address 1 field is required ");
            errors.add("City field is required ");
            errors.add("Country field is required ");
            errors.add("Zip Code field is required ");
            return errors;
        }
        if (!StringUtils.hasLength(addressDto.getAddress1())) {
            errors.add("Address 1 field is required '");
        }
        if (!StringUtils.hasLength(addressDto.getCity())) {
            errors.add("City field is required '");
        }
        if (!StringUtils.hasLength(addressDto.getCountry())) {
            errors.add("Country field is required '");
        }
        if (!StringUtils.hasLength(addressDto.getZipCode())) {
            errors.add("Zip Code field is required ");
        }
        return errors;
    }


}
