package com.veljko121.backend.dto.events;

import java.time.LocalDateTime;
import java.util.Collection;

import com.veljko121.backend.core.enums.EventStatus;
import com.veljko121.backend.dto.OrganizerResponseDTO;
import com.veljko121.backend.dto.RoomReservationResponseDTO;

import lombok.Data;

@Data
public class EventResponseDTO {

    private Integer id;
    private String name;
    private String description;
    private LocalDateTime startDateTime;
    private Integer durationMinutes;
    private Integer ticketsNumber;
    private Integer price;
    private OrganizerResponseDTO organizer;
    private LocalDateTime createdDateTime;
    private RoomReservationResponseDTO roomReservation;
    private EventStatus status;
    private Collection<EventPictureResponseDTO> pictures;
    
}
