package com.apellikka.fotosoppi.foto_soppi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    public SecurityConfiguration() {}

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAuthenticationEntryPoint authEntryPoint) throws Exception {
        http
            .exceptionHandling((exceptionHandling) -> 
                exceptionHandling.authenticationEntryPoint(authEntryPoint))
            .csrf((csrf) -> csrf.disable())
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/images/**").permitAll()
                .requestMatchers("/users/**").permitAll()
                .anyRequest().authenticated()
                // TODO: Configure CSRF for production use
                // TODO: SessionManagement for JWT
            );

        return http.build();
    }
    
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    BCryptPasswordEncoder BCryptPasswordEncoder() {
         return new BCryptPasswordEncoder();
    }
}
