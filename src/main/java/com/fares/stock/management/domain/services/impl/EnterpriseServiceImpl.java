package com.fares.stock.management.domain.services.impl;

import com.fares.stock.management.core.exception.EntityNotFoundException;
import com.fares.stock.management.core.exception.ErrorCodes;
import com.fares.stock.management.core.exception.InvalidEntityException;
import com.fares.stock.management.core.validators.EnterpriseValidator;
import com.fares.stock.management.domain.dto.address.AddressDto;
import com.fares.stock.management.domain.dto.enterprise.EnterpriseDto;
import com.fares.stock.management.domain.dto.roles.RolesDto;
import com.fares.stock.management.domain.dto.user.UserDto;
import com.fares.stock.management.domain.repository.jpa.EnterpriseRepository;
import com.fares.stock.management.domain.repository.jpa.RolesRepository;
import com.fares.stock.management.domain.services.EnterpriseService;
import com.fares.stock.management.domain.services.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(rollbackOn = Exception.class)
@Service
public class EnterpriseServiceImpl implements EnterpriseService {

    private static final Logger log = LoggerFactory.getLogger(EnterpriseServiceImpl.class);
    private final EnterpriseRepository enterpriseRepository;
    private final UserService userService;
    private final RolesRepository rolesRepository;

    @Autowired
    public EnterpriseServiceImpl(EnterpriseRepository enterpriseRepository, UserService userService, RolesRepository rolesRepository) {
        this.enterpriseRepository = enterpriseRepository;
        this.userService = userService;
        this.rolesRepository = rolesRepository;
    }




    @Override
    public EnterpriseDto save(EnterpriseDto enterpriseDto) {
        List<String> errors = EnterpriseValidator.validate(enterpriseDto);
        if (!errors.isEmpty()) {
            log.error("Enterprise is not valid {}", enterpriseDto);
            throw new InvalidEntityException("The enterprise is not valid", ErrorCodes.ENTERPRISE_NOT_VALID, errors);
        }
        EnterpriseDto savedEnterprise = EnterpriseDto.fromEntity(
                enterpriseRepository.save(EnterpriseDto.toEntity(enterpriseDto))
        );

        UserDto user = fromEnterprise(savedEnterprise);

        UserDto savedUser = userService.save(user);

        RolesDto rolesDto = new RolesDto();
                rolesDto.setRoleName("ADMIN");
                rolesDto.setUser(savedUser);


        rolesRepository.save(RolesDto.toEntity(rolesDto));

        return  savedEnterprise;
    }

    private UserDto fromEnterprise(EnterpriseDto dto) {
        UserDto userDto = new UserDto();
        userDto.setAddress (AddressDto.fromEntity(dto.getAddress()) );
        userDto.setFirstName(dto.getName());
        userDto.setLastName(dto.getCodeFiscal());
        userDto.setEmail(dto.getEmail());
        userDto.setPassword(generateRandomPassword());
        userDto.setEnterprise(dto);
        userDto.setBirthDate(Instant.now());
        userDto.setPhoto(dto.getPhoto());

        return userDto;
    }

    private String generateRandomPassword() {
        return "som3R@nd0mP@$$word";
    }

    @Override
    public EnterpriseDto findById(Integer id) {
        if (id == null) {
            log.error("Entreprise ID is null");
            return null;
        }
        return enterpriseRepository.findById(id)
                .map(EnterpriseDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No company with the ID = " + id + " has been found in the DB",
                        ErrorCodes.ENTERPRISE_NOT_FOUND)
                );
    }

    @Override
    public List<EnterpriseDto> findAll() {
        return enterpriseRepository.findAll().stream()
                .map(EnterpriseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Enterprise ID is null");
            return;
        }
        enterpriseRepository.deleteById(id);
    }


}