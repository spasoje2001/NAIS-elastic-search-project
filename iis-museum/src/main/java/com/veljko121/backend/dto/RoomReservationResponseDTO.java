package com.veljko121.backend.dto;

import java.time.LocalDateTime;

import lombok.Data;
import org.springframework.cglib.core.Local;

@Data
public class RoomReservationResponseDTO {

    private Integer id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private RoomResponseDTO room;
/*
    public LocalDateTime getEndDateTime() {
        return startDateTime.plusMinutes(durationMinutes);
    }

 */
    
}
