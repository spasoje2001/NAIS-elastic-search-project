package com.veljko121.backend.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veljko121.backend.model.Organizer;
public interface OrganizerRepository extends JpaRepository<Organizer, Integer> {

    Optional<Organizer> findByUsername(String username);

}