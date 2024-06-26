package com.veljko121.backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.veljko121.backend.core.enums.CleaningStatus;
import com.veljko121.backend.model.Curator;
import com.veljko121.backend.model.Restaurateur;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CleaningJournalResponseDTO {
    
    private Integer cleaningId;
    private String text;
    private LocalDate startDate;
    private LocalDate endDate;
    private CleaningStatus status;
    private Curator curator;
    private Restaurateur restaurateur;
    private Integer itemId;
    private String denialReason;
    private LocalDate putToCleaningTime;
    private LocalDate finishCleaningTime;
    private Integer version;
    private String operationType;
    private LocalDateTime changeDate;

}
