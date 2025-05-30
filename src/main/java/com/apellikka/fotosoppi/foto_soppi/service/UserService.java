package com.apellikka.fotosoppi.foto_soppi.service;

import org.springframework.beans.factory.annotation.Autowired;
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

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public String addUser(String userJSON) {
        // Convert the JSON string to FotoSoppiUser object
        // TODO: Check if the user already exists in the repository
        // then use a service to salt and hash the password and save to db.
        ObjectMapper objectMapper = new ObjectMapper();
        FotoSoppiUser user = null;
        try {
            user = objectMapper.readValue(userJSON, FotoSoppiUser.class);
        } catch (JsonMappingException e) {
            System.err.println("Error mapping JSON to FotoSoppiUser: " + e.getMessage());
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            System.err.println("Error processing JSON: " + e.getMessage());
            e.printStackTrace();
        }
        
        if(userExists(user.getUsername())) {
            System.out.println("User already exists: " + user.getUsername());
            return "User already exists: " + user.getUsername();
        } 
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            System.out.println("User object created: " + user.getPassword()); 
            System.out.println("User saved to repository: " + user.getUsername());
            return "User created successfully: " + user.getUsername();
        }
    }

    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

}
