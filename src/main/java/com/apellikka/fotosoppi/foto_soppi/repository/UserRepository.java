package com.apellikka.fotosoppi.foto_soppi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apellikka.fotosoppi.foto_soppi.entity.FotoSoppiUser;

@Repository
public interface UserRepository extends JpaRepository<FotoSoppiUser, Long>  {
    Optional<FotoSoppiUser> findByUsername(String userName);
}
