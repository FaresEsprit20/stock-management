package com.fares.stock.management.domain.controllers;

import com.fares.stock.management.domain.controllers.api.AccountManagementApi;
import com.fares.stock.management.domain.dto.auth.ChangePasswordUserDto;
import com.fares.stock.management.domain.dto.user.UserDto;
import com.fares.stock.management.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountManagementController implements AccountManagementApi {

    private final UserService userService;

    @Autowired
    public AccountManagementController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDto toggleLockAccount(Integer accountId) {
        return userService.toggleLock(accountId);
    }


}
