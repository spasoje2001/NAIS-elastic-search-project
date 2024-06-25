package com.veljko121.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veljko121.backend.model.Guest;

public interface GuestRepository extends JpaRepository<Guest, Integer> {

    Optional<Guest> findByUsername(String username);

}
