package com.fares.stock.management.domain.controllers;

import com.fares.stock.management.domain.controllers.api.AuthenticationApi;
import com.fares.stock.management.domain.dto.auth.AuthenticationRequest;
import com.fares.stock.management.domain.dto.auth.AuthenticationResponse;
import com.fares.stock.management.domain.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AuthenticationController implements AuthenticationApi {


    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }



    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }


}

