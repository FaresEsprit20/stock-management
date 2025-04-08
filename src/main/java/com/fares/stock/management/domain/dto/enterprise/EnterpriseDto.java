package com.fares.stock.management.domain.dto.enterprise;

import com.fares.stock.management.domain.entities.Address;
import com.fares.stock.management.domain.entities.Enterprise;
import com.fares.stock.management.domain.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EnterpriseDto {

    private Integer id;
    // Setters
    // Getters
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

