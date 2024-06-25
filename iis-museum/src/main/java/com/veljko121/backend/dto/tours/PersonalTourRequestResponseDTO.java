package com.veljko121.backend.dto.tours;

import com.veljko121.backend.core.enums.PersonalTourRequestStatus;
import com.veljko121.backend.dto.ExhibitionResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PersonalTourRequestResponseDTO {

    private Integer id;

    private LocalDateTime occurrenceDateTime;

    private String guestNumber;

    private List<ExhibitionResponseDTO> exhibitions = new ArrayList<>();

    private Integer proposerId;

    private Integer organizerId;

    private PersonalTourRequestStatus status;

    private String proposerContactPhone;

    private String denialReason;

}
