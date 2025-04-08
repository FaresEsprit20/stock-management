package com.fares.stock.management.domain.controllers;

import com.fares.stock.management.domain.controllers.api.UserApi;
import com.fares.stock.management.domain.dto.user.UserDto;
import com.fares.stock.management.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements UserApi {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDto save(UserDto userDto) {
        return userService.save(userDto);
    }

    @Override
    public UserDto changePassword(ChangePasswordUserDto dto) {
        return userService.changePassword(dto);
    }

    @Override
    public UserDto findById(Integer id) {
        return userService.findById(id);
    }

    @Override
    public UserDto findByEmail(String email) {
        return userService.findByEmail(email);
    }

    @Override
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @Override
    public void delete(Integer id) {
        userService.delete(id);
    }

}
