package com.fares.stock.management.domain.controllers.api;

import com.fares.stock.management.domain.dto.auth.AuthenticationRequest;
import com.fares.stock.management.domain.dto.auth.AuthenticationResponse;
import com.fares.stock.management.domain.dto.user.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import static com.fares.stock.management.core.utils.constants.Constants.ACCOUNTS_ENDPOINT;

@Tag(name = "Account Management", description = "API for account management")
public interface AccountManagementApi {


        @Operation(summary = "Lock/Unlock user",
                description = "Locks or Unlocks a Manager User Account by an ADMIN User")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Lock successful"),
                @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid operation"),
                @ApiResponse(responseCode = "400", description = "Bad request - Invalid input")
        })
        @PutMapping(ACCOUNTS_ENDPOINT + "/lock/toggle/{accountId}")
        UserDto toggleLockAccount(@PathVariable Integer accountId);


    }

