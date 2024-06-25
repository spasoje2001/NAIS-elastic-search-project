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
public class PersonalTourRequestUpdateDTO {

    private Integer id;

    private PersonalTourRequestStatus status;

    private String denialReason;

    private List<ExhibitionResponseDTO> exhibitions = new ArrayList<>();

    private String guestNumber;

    private LocalDateTime occurrenceDateTime;

    private String proposerContactPhone;

}
