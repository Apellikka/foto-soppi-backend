package com.apellikka.fotosoppi.foto_soppi.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.apellikka.fotosoppi.foto_soppi.entity.FotoSoppiUser;
import com.apellikka.fotosoppi.foto_soppi.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<FotoSoppiUser> userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent()) {
            FotoSoppiUser user = userOptional.get();
            return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }   
}
