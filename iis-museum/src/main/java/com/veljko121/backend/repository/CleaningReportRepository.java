package com.veljko121.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.veljko121.backend.model.CleaningReport;

public interface CleaningReportRepository extends JpaRepository<CleaningReport, Integer> {
    
}
