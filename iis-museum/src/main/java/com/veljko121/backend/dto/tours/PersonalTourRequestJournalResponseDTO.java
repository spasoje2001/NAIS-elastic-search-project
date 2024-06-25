package com.veljko121.backend.dto.tours;

import com.veljko121.backend.core.enums.PersonalTourRequestStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PersonalTourRequestJournalResponseDTO {

    private Integer id;

    private String operation;

    private String guestNumber;

    private LocalDateTime occurrenceDateTime;

    private LocalDateTime dat;

    private Integer personalTourRequestId;

    private Integer proposerId;

    private Integer organizerId;

    private PersonalTourRequestStatus status;

    private String denialReason;

    private String proposerContactPhone;

    private Integer vers;

}
