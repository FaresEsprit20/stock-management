package com.fares.stock.management.domain.services;

import com.fares.stock.management.domain.dto.auth.AuthenticationRequest;
import com.fares.stock.management.domain.dto.auth.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.User;

import java.io.IOException;

public interface AuthenticationService {


    AuthenticationResponse authenticate(AuthenticationRequest request);

    void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException;


}
