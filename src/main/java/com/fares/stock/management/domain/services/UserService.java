package com.fares.stock.management.domain.services;

import com.fares.stock.management.domain.dto.auth.ChangePasswordUserDto;
import com.fares.stock.management.domain.dto.user.UserDto;

import java.util.List;

public interface UserService {

    UserDto save(UserDto userDto);

    UserDto findById(Integer userId);

    List<UserDto> findAll();

    void delete(Integer userId);

    UserDto findByEmail(String email);

    UserDto changePassword(ChangePasswordUserDto dto);


}