package com.veljko121.backend.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veljko121.backend.model.Restaurateur;
public interface RestaurateurRepository extends JpaRepository<Restaurateur, Integer> {
    
    Optional<Restaurateur> findByUsername(String username);
    
}
