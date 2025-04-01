package com.fares.stock.management.domain.dto.address;

import com.fares.stock.management.domain.entities.Address;

public class AddressDto {

    private String address1;
    private String address2;
    private String city;
    private String zipCode;
    private String country;

    // No-args constructor
    public AddressDto() {
    }

    // All-args constructor
    public AddressDto(String address1, String address2, String city, String zipCode, String country) {
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

    // Getters
    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }

    // Setters
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setTown(String city) {
        this.city = city;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

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
        address.setTown(addressDto.getCity());
        address.setZipCode(addressDto.getZipCode());
        address.setCountry(addressDto.getCountry());
        return address;
    }
}
