package com.veljko121.backend.events.eventRelationalDatabase;

import java.util.Date;
import java.util.UUID;

import com.veljko121.backend.dto.events.EventResponseDTO;
import com.veljko121.backend.events.Event;

import lombok.Data;

@Data
public class MuseumRelationalEvent implements Event {

    private final UUID eventId = UUID.randomUUID();
    private final Date date = new Date();
    private EventResponseDTO eventResponseDTO;
    private MuseumEventRelationalStatus status;

    public MuseumRelationalEvent(EventResponseDTO eventResponseDTO, MuseumEventRelationalStatus status) {
        super();
        this.eventResponseDTO = eventResponseDTO;
        this.status = status;
    }
    
}

