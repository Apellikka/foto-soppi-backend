package com.apellikka.fotosoppi.foto_soppi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apellikka.fotosoppi.foto_soppi.entity.FotoSoppiUser;
import com.apellikka.fotosoppi.foto_soppi.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public String postMethodName(@RequestBody String entity) { 
        System.out.println("Entity received: " + entity);
        // Convert the JSON string to FotoSoppiUser object
        // then use a service to salt and hash the password and save to db.
        return entity;
    }
    
}
