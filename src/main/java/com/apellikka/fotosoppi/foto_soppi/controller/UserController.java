package com.apellikka.fotosoppi.foto_soppi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apellikka.fotosoppi.foto_soppi.service.UserApiResponse;
import com.apellikka.fotosoppi.foto_soppi.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserApiResponse> postMethodName(@RequestBody String entity){ 
        System.out.println("Entity received: " + entity);
        UserApiResponse response = userService.addUser(entity);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
