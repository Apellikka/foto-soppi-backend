package com.apellikka.fotosoppi.foto_soppi.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class FotoSoppiUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;

    // Default constructor for JPA. Needs to be there for Hibernate to work properly.
    // If you don't have this, you might get an error like 
    // "No default constructor for entity: com.apellikka.fotosoppi.foto_soppi.entity.FotoSoppiUser"
    @SuppressWarnings("unused")
    private FotoSoppiUser() {};

    public FotoSoppiUser(String username, String password) {
        this.username = username;
        this.password = password;    
    }

    public void setPassword(String password) {
      this.password = password; 
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
    }

    @Override
    public String getPassword() {
        return this.password;
    }
    @Override
    public String getUsername() {
        return this.username;    
    }
}