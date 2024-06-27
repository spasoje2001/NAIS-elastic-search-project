package com.veljko121.backend.dto.tours;

import com.veljko121.backend.core.enums.TourCategory;
import com.veljko121.backend.dto.ExhibitionResponseDTO;
import com.veljko121.backend.model.Exhibition;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TourCreateDTO {

    private String name;

    private String description;

    private List<ExhibitionResponseDTO> exhibitions = new ArrayList<>();

    private String duration;

    private LocalDateTime occurrenceDateTime;

    private String adultTicketPrice;

    private String minorTicketPrice;

    private Integer guideId;

    private String capacity;

    private String picturePath;

    private TourCategory category;
}
