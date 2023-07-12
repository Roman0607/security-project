package com.example.securityproject.service;

import com.example.securityproject.models.User;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @CachePut(value = "session")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.findByName(username);

        if(user.isPresent()) {
            UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(user.get().getPassword());
            builder.roles("USER");
            return builder.build();
        }

        return (UserDetails) new UsernameNotFoundException("User not found.");
    }
}
