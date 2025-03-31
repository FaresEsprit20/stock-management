package com.fares.stock.management.domain.dto.customer;

import com.fares.stock.management.domain.entities.Customer;
import com.fares.stock.management.domain.entities.Address;

public class CustomerDto {

    private String firstName;
    private String lastName;
    private Address address;
    private String photo;
    private String email;
    private String numTel;
    private Integer idEnterprise;

    // No-args constructor
    public CustomerDto() {
    }

    // All-args constructor
    public CustomerDto(String firstName, String lastName, Address address, String photo, String email, String numTel, Integer idEnterprise) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.photo = photo;
        this.email = email;
        this.numTel = numTel;
        this.idEnterprise = idEnterprise;
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public Integer getIdEnterprise() {
        return idEnterprise;
    }

    public void setIdEnterprise(Integer idEnterprise) {
        this.idEnterprise = idEnterprise;
    }

    // Conversion methods
    public static CustomerDto fromEntity(Customer customer) {
        if (customer == null) {
            return null;
        }
        return new CustomerDto(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getAddress(),
                customer.getPhoto(),
                customer.getEmail(),
                customer.getNumTel(),
                customer.getIdEnterprise()
        );
    }

    public static Customer toEntity(CustomerDto customerDto) {
        if (customerDto == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setAddress(customerDto.getAddress());
        customer.setPhoto(customerDto.getPhoto());
        customer.setEmail(customerDto.getEmail());
        customer.setNumTel(customerDto.getNumTel());
        customer.setIdEnterprise(customerDto.getIdEnterprise());
        return customer;
    }
}

