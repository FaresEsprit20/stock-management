package com.fares.stock.management.domain.entities;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class ExtendedUser implements UserDetails {

    private final String email;
    private final String password;
    private final Integer enterpriseId;
    private final Collection<? extends GrantedAuthority> authorities;

    public ExtendedUser(String email, String password,
                        Integer enterpriseId,
                        Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.password = password;
        this.enterpriseId = enterpriseId;
        this.authorities = authorities;
    }

    // Required UserDetails methods
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return email; }

    // Account status flags (customize as needed)
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    // Custom field
    public Integer getEnterpriseId() { return enterpriseId; }

}