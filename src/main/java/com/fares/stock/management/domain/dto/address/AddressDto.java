package com.fares.stock.management.domain.dto.address;

import com.fares.stock.management.domain.entities.Address;
import lombok.*;

@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {

    private String address1;
    private String address2;
    private String city;
    private String zipCode;
    private String country;



    // Static conversion methods
    public static AddressDto fromEntity(Address address) {
        if (address == null) {
            return null;
        }
        return new AddressDto(
                address.getAddress1(),
                address.getAddress2(),
                address.getCity(),
                address.getZipCode(),
                address.getCountry()
        );
    }

    public static Address toEntity(AddressDto addressDto) {
        if (addressDto == null) {
            return null;
        }
        Address address = new Address();
        address.setAddress1(addressDto.getAddress1());
        address.setAddress2(addressDto.getAddress2());
        address.setCity(addressDto.getCity());
        address.setZipCode(addressDto.getZipCode());
        address.setCountry(addressDto.getCountry());
        return address;
    }


}
