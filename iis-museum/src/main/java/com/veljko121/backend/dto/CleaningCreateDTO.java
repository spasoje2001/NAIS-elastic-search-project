package com.veljko121.backend.dto;

import java.time.LocalDate;

import com.veljko121.backend.core.enums.CleaningStatus;

import lombok.Data;

@Data
public class CleaningCreateDTO {
    private String text;
    private LocalDate startDate;
    private LocalDate endDate;
    private CleaningStatus status;
}
