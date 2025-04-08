package com.fares.stock.management.domain.dto.enterprise;

import com.fares.stock.management.domain.entities.Address;
import com.fares.stock.management.domain.entities.Enterprise;
import com.fares.stock.management.domain.entities.User;

import java.util.List;

public class EnterpriseDto {

    private Integer id;
    private String name;
    private String description;
    private Address address;
    private String codeFiscal;
    private String photo;
    private String email;
    private String numTel;
    private String webSite;
    private List<User> users;

    // No-args constructor
    public EnterpriseDto() {
    }

    // All-args constructor
    public EnterpriseDto(String name, String description, Address address,
                         String codeFiscal, String photo, String email, String numTel,
                         String webSite, List<User> users) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.codeFiscal = codeFiscal;
        this.photo = photo;
        this.email = email;
        this.numTel = numTel;
        this.webSite = webSite;
        this.users = users;
    }

    // All-args constructor
    public EnterpriseDto(Integer id, String name, String description, Address address,
                         String codeFiscal, String photo, String email, String numTel,
                         String webSite, List<User> users) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.codeFiscal = codeFiscal;
        this.photo = photo;
        this.email = email;
        this.numTel = numTel;
        this.webSite = webSite;
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Address getAddress() {
        return address;
    }

    public String getCodeFiscal() {
        return codeFiscal;
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

    public String getWebSite() {
        return webSite;
    }

    public List<User> getUsers() {
        return users;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setCodeFiscal(String codeFiscal) {
        this.codeFiscal = codeFiscal;
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

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    // Static conversion methods
    public static EnterpriseDto fromEntity(Enterprise enterprise) {
        if (enterprise == null) {
            return null;
        }
        return new EnterpriseDto(
                enterprise.getId(),
                enterprise.getName(),
                enterprise.getDescription(),
                enterprise.getAddress(),
                enterprise.getCodeFiscal(),
                enterprise.getPhoto(),
                enterprise.getEmail(),
                enterprise.getNumTel(),
                enterprise.getWebSite(),
                enterprise.getUsers()
        );
    }

    public static Enterprise toEntity(EnterpriseDto enterpriseDto) {
        if (enterpriseDto == null) {
            return null;
        }
        Enterprise enterprise = new Enterprise();
        enterprise.setId(enterpriseDto.getId());
        enterprise.setName(enterpriseDto.getName());
        enterprise.setDescription(enterpriseDto.getDescription());
        enterprise.setAddress(enterpriseDto.getAddress());
        enterprise.setCodeFiscal(enterpriseDto.getCodeFiscal());
        enterprise.setPhoto(enterpriseDto.getPhoto());
        enterprise.setEmail(enterpriseDto.getEmail());
        enterprise.setNumTel(enterpriseDto.getNumTel());
        enterprise.setWebSite(enterpriseDto.getWebSite());
        enterprise.setUsers(enterpriseDto.getUsers());
        return enterprise;
    }


}

