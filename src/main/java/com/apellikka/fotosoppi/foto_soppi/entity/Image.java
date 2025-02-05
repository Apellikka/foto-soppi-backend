package com.apellikka.fotosoppi.foto_soppi.entity;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "images")
public class Image extends RepresentationModel<Image> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String path;

    public Image() {}

    @JsonCreator
    public Image(@JsonProperty("id") Integer id, @JsonProperty("path") String path) 
    {
        this.id = id;
        this.path = path;
    }

    public Integer getId()
    {
        return this.id;
    }

    public String getPath()
    {
        return this.path;
    }
}
