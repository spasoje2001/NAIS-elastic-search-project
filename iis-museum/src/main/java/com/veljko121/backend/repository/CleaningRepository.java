package com.veljko121.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veljko121.backend.model.Cleaning;

public interface CleaningRepository extends JpaRepository<Cleaning, Integer>{
    
}
