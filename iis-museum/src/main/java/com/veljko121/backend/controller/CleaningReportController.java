package com.veljko121.backend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.veljko121.backend.dto.CleaningReportDTO;
import com.veljko121.backend.model.CleaningReport;
import com.veljko121.backend.service.ICleaningReportService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cleaningReport")
@RequiredArgsConstructor
public class CleaningReportController {

    private final ICleaningReportService cleaningReportService;

    @PostMapping
    public ResponseEntity<CleaningReport> writeReport(@RequestBody CleaningReportDTO cleaningReportDTO) {
        CleaningReport cleaningReport = new CleaningReport();
        cleaningReport.setText(cleaningReportDTO.getText());
        cleaningReport.setCleaningId(cleaningReportDTO.getCleaningId());
        cleaningReport.setRestaurteurId(cleaningReportDTO.getRestaurateurId());
        CleaningReport report = cleaningReportService.save(cleaningReport);
        return new ResponseEntity<>(report, HttpStatus.CREATED);
    }
    
}
