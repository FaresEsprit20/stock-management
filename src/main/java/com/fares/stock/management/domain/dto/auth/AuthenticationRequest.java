package com.fares.stock.management.domain.dto.auth;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequest {

    private String login;   // Username (Login)
    private String password; // User's password

}