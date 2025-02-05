package com.apellikka.fotosoppi.foto_soppi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.apellikka.fotosoppi.foto_soppi.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfiguration(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean 
    UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/images/**").permitAll()
                .requestMatchers("/login").permitAll()
                .anyRequest().authenticated()
            );

        return http.build();
    }

    @Bean
    AuthenticationProvider authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(BCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    BCryptPasswordEncoder BCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
