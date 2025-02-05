package com.apellikka.fotosoppi.foto_soppi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apellikka.fotosoppi.foto_soppi.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
        
}
