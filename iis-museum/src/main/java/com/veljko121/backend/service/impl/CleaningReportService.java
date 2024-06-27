package com.veljko121.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.veljko121.backend.core.service.impl.CRUDService;
import com.veljko121.backend.model.CleaningReport;
import com.veljko121.backend.repository.CleaningReportRepository;
import com.veljko121.backend.service.ICleaningReportService;

@Service
public class CleaningReportService extends CRUDService<CleaningReport, Integer> implements ICleaningReportService{


    private final CleaningReportRepository cleaningReportRepository;

    @Autowired
    public CleaningReportService(CleaningReportRepository repository, CleaningReportRepository cleaningReportRepository) {
        super(repository);
        this.cleaningReportRepository = cleaningReportRepository;
    }

    @Override
    public CleaningReport save(CleaningReport cleaningReport) {
        return cleaningReportRepository.save(cleaningReport);
    }
}
