package com.veljko121.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.veljko121.backend.model.CleaningJournal;

public interface CleaningJournalRepository extends JpaRepository<CleaningJournal, Integer> {
    
}
