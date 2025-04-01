package com.fares.stock.management.core.validators.auth;

import com.fares.stock.management.core.constants.Constants;
import com.fares.stock.management.domain.dto.auth.AuthenticationRequest;

public class AuthenticationRequestValidator {

    public static void validate(AuthenticationRequest request) {
        // Validate login (username)
        if (request.getLogin() == null || request.getLogin().trim().isEmpty()) {
            throw new IllegalArgumentException("Login (username) must not be null or empty");
        }
        if (request.getLogin().length() < Constants.MIN_LOGIN_LENGTH ||
                request.getLogin().length() > Constants.MAX_LOGIN_LENGTH) {
            throw new IllegalArgumentException("Login (username) must be between " + Constants.MIN_LOGIN_LENGTH +
                    " and " + Constants.MAX_LOGIN_LENGTH + " characters");
        }
        if (!request.getLogin().matches(Constants.LOGIN_REGEX)) {
            throw new IllegalArgumentException("Login (username) must be alphanumeric, " +
                    "and can include underscores and hyphens");
        }

        // Validate password
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password must not be null or empty");
        }
        if (request.getPassword().length() < Constants.MIN_PASSWORD_LENGTH ||
                request.getPassword().length() > Constants.MAX_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Password must be between " + Constants.MIN_PASSWORD_LENGTH +
                    " and " + Constants.MAX_PASSWORD_LENGTH + " characters");
        }
        if (!request.getPassword().matches(Constants.PASSWORD_REGEX)) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter, one lowercase letter, " +
                    "one number, and one special character");
        }
    }
}
