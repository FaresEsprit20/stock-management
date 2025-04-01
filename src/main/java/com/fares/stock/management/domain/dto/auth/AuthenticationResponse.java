package com.fares.stock.management.domain.dto.auth;

public class AuthenticationResponse {

    private String accessToken;  // The access token

    // No-args constructor
    public AuthenticationResponse() {}

    // All-args constructor
    public AuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    // Getter
    public String getAccessToken() {
        return accessToken;
    }

    // Setter
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    // toString() method for printing the object
    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "accessToken='" + accessToken + '\'' +
                '}';
    }

    // equals() method to compare two AuthenticationResponse objects
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AuthenticationResponse that = (AuthenticationResponse) obj;
        return accessToken.equals(that.accessToken);
    }

    // hashCode() method to generate a unique hash code for the object
    @Override
    public int hashCode() {
        return accessToken.hashCode();
    }


}
