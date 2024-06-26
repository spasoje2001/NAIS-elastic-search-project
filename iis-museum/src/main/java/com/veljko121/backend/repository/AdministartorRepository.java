package com.veljko121.backend.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veljko121.backend.model.Administrator;
public interface AdministartorRepository extends JpaRepository<Administrator, Integer> {

    Optional<Administrator> findByUsername(String username);

}
