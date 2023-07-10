package com.example.securityproject.config;

import com.example.securityproject.models.User;
import com.example.securityproject.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private final UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserService userService;

    @Value("${service.user.name}")
    private String userName;

    @Value("${service.user.password}")
    private String userPassword;

    @Value("${service.user.roles}")
    private String userRole;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder);
    }

    @PostConstruct
    void init() {
        User mAdmin = userService.findByName(this.userName)
                .orElseGet(() ->
                        User.builder()
                                .userName(this.userName)
                                .role(this.userRole)
                                .build()
                );

        mAdmin.setPassword(this.userPassword);
        mAdmin.setCreatedBy("service");
        userService.save(mAdmin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers(headers -> headers.cacheControl(HeadersConfigurer.CacheControlConfig::disable))
                .cors()
                .and()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        return http.build();
    }

}

