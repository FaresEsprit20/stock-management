package com.fares.stock.management.domain.controllers.api;

import com.fares.stock.management.domain.dto.auth.AuthenticationRequest;
import com.fares.stock.management.domain.dto.auth.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.fares.stock.management.core.utils.constants.Constants.AUTHENTICATION_ENDPOINT;

@Tag(name = "Authentication", description = "API for user authentication")
public interface AuthenticationApi {

    @PostMapping(AUTHENTICATION_ENDPOINT + "/authenticate")
    @Operation(summary = "Authenticate user",
            description = "Authenticates user credentials and returns JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials"),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input")
    })
    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request);
}