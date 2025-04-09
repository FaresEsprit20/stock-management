package com.fares.stock.management.domain.services.impl.auth;

import com.fares.stock.management.domain.dto.user.UserDto;
import com.fares.stock.management.domain.entities.ExtendedUser;
import com.fares.stock.management.domain.entities.User;
import com.fares.stock.management.domain.repository.jpa.UserRepository;
import com.fares.stock.management.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new ExtendedUser(user);  // Convert User to ExtendedUser
    }

}
