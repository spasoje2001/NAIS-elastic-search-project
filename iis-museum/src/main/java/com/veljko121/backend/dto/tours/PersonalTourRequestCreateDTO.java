package com.veljko121.backend.dto.tours;

import com.veljko121.backend.dto.ExhibitionResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PersonalTourRequestCreateDTO {

    private List<ExhibitionResponseDTO> exhibitions = new ArrayList<>();

    private String guestNumber;

    private LocalDateTime occurrenceDateTime;

    private String proposerContactPhone;

}
