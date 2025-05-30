package com.apellikka.fotosoppi.foto_soppi.service;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apellikka.fotosoppi.foto_soppi.entity.FotoSoppiUser;
import com.apellikka.fotosoppi.foto_soppi.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserService {
    
    
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public UserService(
        PasswordEncoder passwordEncoder, 
        UserRepository userRepository, 
        AuthenticationManager authenticationManager) {
        
            this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public UserApiResponse registerUser(String userJSON) {
        // TODO MAYBE: Validate password strength and other user details before saving????
        ObjectMapper objectMapper = new ObjectMapper();
        FotoSoppiUser user = null;
        
        try {
            user = objectMapper.readValue(userJSON, FotoSoppiUser.class);
        } catch (JsonMappingException e) {
            System.err.println("Error mapping JSON to FotoSoppiUser: " + e.getMessage());
            return new UserApiResponse(null, "Invalid JSON format!", HttpStatus.BAD_REQUEST.value());
        } catch (JsonProcessingException e) {
            System.err.println("Error processing JSON: " + e.getMessage());
            return new UserApiResponse(null, "Invalid JSON format!",HttpStatus.BAD_REQUEST.value());
        }
        
        if(userExists(user.getUsername())) {
            System.out.println("User already exists: " + user.getUsername());
            return new UserApiResponse(user.getUsername(), "User already exists!", HttpStatus.CONFLICT.value());
        } 
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            System.out.println("User object created: password: " + user.getPassword()); 
            System.out.println("User saved to repository: " + user.getUsername());
            return new UserApiResponse(user.getUsername(), "User created successfully!", HttpStatus.CREATED.value());
        }
    }

    public UserApiResponse authenticate(String userJSON) {        
       authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userJSON, userJSON)
        );

        return new UserApiResponse(
            "Login not implemented yet", 
            "Login functionality is not yet available.", 
            HttpStatus.OK.value()
        );
    }

    private boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    } 
}
