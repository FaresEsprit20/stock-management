package com.fares.stock.management.domain.services.impl;

import com.fares.stock.management.core.exception.EntityNotFoundException;
import com.fares.stock.management.core.exception.ErrorCodes;
import com.fares.stock.management.core.exception.InvalidEntityException;
import com.fares.stock.management.core.exception.InvalidOperationException;
import com.fares.stock.management.core.validators.UserValidator;
import com.fares.stock.management.domain.dto.auth.ChangePasswordUserDto;
import com.fares.stock.management.domain.dto.user.UserDto;
import com.fares.stock.management.domain.entities.User;
import com.fares.stock.management.domain.repository.jpa.UserRepository;
import com.fares.stock.management.domain.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDto save(UserDto dto) {
        List<String> errors = UserValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error(" User is not valid {}", dto);
            throw new InvalidEntityException("The user is not valid", ErrorCodes.USER_NOT_VALID, errors);
        }

        if(userAlreadyExists(dto.getEmail())) {
            throw new InvalidEntityException("Another user with the same email already exists", ErrorCodes.USER_ALREADY_EXISTS,
                    Collections.singletonList("Another user with the same email already exists in the DB"));
        }


        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        return UserDto.fromEntity(
                userRepository.save(
                        UserDto.toEntity(dto)
                )
        );
    }

    private boolean userAlreadyExists(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        return user.isPresent();
    }

    @Override
    public UserDto findById(Integer userId) {

        if (userId == null) {
            log.error("User ID is null");
            throw new InvalidEntityException("The user ID is not valid", ErrorCodes.USER_NOT_VALID);
        }
        return userRepository.findById(userId)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No user with the ID = " + userId + " has been found in the DB",
                        ErrorCodes.USER_NOT_FOUND)
                );
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer userId) {
        if (userId == null) {
            log.error("User ID is null");
            throw new InvalidEntityException("The user ID is not valid", ErrorCodes.USER_NOT_VALID);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public UserDto findByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No user with the email = " + email + " has been found in the DB",
                        ErrorCodes.USER_NOT_FOUND)
                );
    }

    @Override
    public UserDto changePassword(ChangePasswordUserDto dto) {
        validate(dto);
        Optional<User> utilisateurOptional = userRepository.findById(dto.getId());
        if (utilisateurOptional.isEmpty()) {
            log.warn("No User has been found with the ID " + dto.getId());
            throw new EntityNotFoundException("No User has been found with the ID " + dto.getId(), ErrorCodes.USER_NOT_FOUND);
        }

        User user = utilisateurOptional.get();
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        return UserDto.fromEntity(
                userRepository.save(user)
        );
    }

    private void validate(ChangePasswordUserDto dto) {
        if (dto == null) {
            log.warn("Impossible to modify the password with a null object");
            throw new InvalidOperationException("No Information has been provided to proceed for changing the password",
                    ErrorCodes.USER_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (dto.getId() == null) {
            log.warn("Impossible to modify the password with a NULL ID");
            throw new InvalidOperationException("ID user is  null:: Impossible to modify the password ",
                    ErrorCodes.USER_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (!StringUtils.hasLength(dto.getPassword()) || !StringUtils.hasLength(dto.getConfirmPassword())) {
            log.warn("Impossible to modify the password with a NULL password");
            throw new InvalidOperationException("Null Password:: Impossible to modify the password",
                    ErrorCodes.USER_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            log.warn("Impossible to modify the password when your password and confirm password are not the same ");
            throw new InvalidOperationException("User Passwords mismatch:: Impossible to modify the password when your password and confirm password are not the same",
                    ErrorCodes.USER_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
    }



}
