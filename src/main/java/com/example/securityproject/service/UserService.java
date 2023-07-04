package com.example.securityproject.service;

import com.example.securityproject.models.User;
import com.example.securityproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public Optional<User> findByName(String userName) {
        return userRepository.findUserByUserName(userName);
    }

    public User save(User user) {
        String userPassword = user.getPassword();
        String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        user.setPassword(encoder.encode(userPassword));
        user.setCreatedBy(loggedInUserName);
        return userRepository.save(user);
    }
}
