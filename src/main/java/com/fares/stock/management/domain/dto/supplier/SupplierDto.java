package com.fares.stock.management.domain.dto.supplier;


import com.fares.stock.management.domain.entities.Address;
import com.fares.stock.management.domain.entities.Supplier;


public class SupplierDto {

    private String firstName;
    private String lastName;
    private Address address;
    private String photo;
    private String email;
    private String numTel;
    private Integer idEnterprise;

    // No-args constructor
    public SupplierDto() {
    }

    // All-args constructor
    public SupplierDto(String firstName, String lastName, Address address, String photo,
                       String email, String numTel, Integer idEnterprise) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.photo = photo;
        this.email = email;
        this.numTel = numTel;
        this.idEnterprise = idEnterprise;
    }

    // Getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhoto() {
        return photo;
    }

    public String getEmail() {
        return email;
    }

    public String getNumTel() {
        return numTel;
    }

    public Integer getIdEnterprise() {
        return idEnterprise;
    }


    // Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public void setIdEnterprise(Integer idEnterprise) {
        this.idEnterprise = idEnterprise;
    }


    // Static conversion methods
    public static SupplierDto fromEntity(Supplier supplier) {
        if (supplier == null) {
            return null;
        }

        return new SupplierDto(
                supplier.getFirstName(),
                supplier.getLastName(),
                supplier.getAddress(),
                supplier.getPhoto(),
                supplier.getEmail(),
                supplier.getNumTel(),
                supplier.getIdEnterprise()
        );
    }

    public static Supplier toEntity(SupplierDto supplierDto) {
        if (supplierDto == null) {
            return null;
        }
        Supplier supplier = new Supplier();
        supplier.setFirstName(supplierDto.getFirstName());
        supplier.setLastName(supplierDto.getLastName());
        supplier.setAddress(supplierDto.getAddress());
        supplier.setPhoto(supplierDto.getPhoto());
        supplier.setEmail(supplierDto.getEmail());
        supplier.setNumTel(supplierDto.getNumTel());
        supplier.setIdEnterprise(supplierDto.getIdEnterprise());
        return supplier;
    }


}
