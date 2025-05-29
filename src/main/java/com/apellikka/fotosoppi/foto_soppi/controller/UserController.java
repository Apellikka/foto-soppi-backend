package com.apellikka.fotosoppi.foto_soppi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apellikka.fotosoppi.foto_soppi.entity.FotoSoppiUser;
import com.apellikka.fotosoppi.foto_soppi.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public String postMethodName(@RequestBody String entity) throws JsonMappingException, JsonProcessingException { 
        System.out.println("Entity received: " + entity);
        // Convert the JSON string to FotoSoppiUser object
        // then use a service to salt and hash the password and save to db.
        ObjectMapper objectMapper = new ObjectMapper();
        FotoSoppiUser user = objectMapper.readValue(entity, FotoSoppiUser.class);
        user.setPassword(passwordEncoder, user.getPassword());
        System.out.println("User object created: " + user.getPassword());
        return entity;
    }
    
}
