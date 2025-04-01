package com.fares.stock.management.domain.dto.auth;

import java.util.Objects;

public class AuthenticationRequest {

    private String login;   // Username (Login)
    private String password; // User's password

    // No-args constructor
    public AuthenticationRequest() {
    }

    // All-args constructor
    public AuthenticationRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    // Getters
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString() method for printing the object
    @Override
    public String toString() {
        return "AuthenticationRequest{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    // equals() method to compare two AuthenticationRequest objects


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationRequest that = (AuthenticationRequest) o;
        return Objects.equals(login, that.login) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }


}