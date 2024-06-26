package com.veljko121.backend.events.eventElasticSearchDatabase;

import java.util.Date;
import java.util.UUID;

import com.veljko121.backend.dto.events.EventResponseDTO;
import com.veljko121.backend.events.Event;

import lombok.Data;

@Data
public class MuseumElasticEvent implements Event {

    private final UUID eventId = UUID.randomUUID();
    private final Date date = new Date();
    private EventResponseDTO eventResponseDTO;
    private MuseumEventElasticStatus status;

    public MuseumElasticEvent(EventResponseDTO eventResponseDTO, MuseumEventElasticStatus status) {
        super();
        this.eventResponseDTO = eventResponseDTO;
        this.status = status;
    }
    
}
