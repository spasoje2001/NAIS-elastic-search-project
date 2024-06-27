package com.veljko121.backend.dto;

import com.veljko121.backend.core.enums.ExhibitionStatus;
import com.veljko121.backend.core.enums.ExhibitionTheme;
import lombok.Data;

import java.util.List;

@Data
public class ExhibitionResponseDTO {
    private Integer id;
    private String name;
    private String picture;
    private String shortDescription;
    private String longDescription;
    private ExhibitionTheme theme;
    private ExhibitionStatus status;
    private String startDate;
    private String endDate;
    private Integer price;
    private OrganizerResponseDTO organizer; // Assuming OrganizerResponseDTO is defined
    private CuratorResponseDTO curator; // Assuming CuratorResponseDTO is defined
    private RoomReservationResponseDTO roomReservation; // Assuming RoomReservationResponseDTO is defined
}