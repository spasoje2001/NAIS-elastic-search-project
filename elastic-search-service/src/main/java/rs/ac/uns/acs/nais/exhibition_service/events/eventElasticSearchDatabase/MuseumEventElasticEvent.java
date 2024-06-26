package rs.ac.uns.acs.nais.exhibition_service.events.eventElasticSearchDatabase;

import java.util.Date;
import java.util.UUID;

import lombok.Data;
import rs.ac.uns.acs.nais.exhibition_service.dto.EventResponseDTO;
import rs.ac.uns.acs.nais.exhibition_service.events.Event;

@Data
public class MuseumEventElasticEvent implements Event {

    private final UUID eventId = UUID.randomUUID();
    private final Date date = new Date();
    private EventResponseDTO eventResponseDTO;
    private MuseumEventElasticStatus status;

    public MuseumEventElasticEvent(EventResponseDTO eventResponseDTO, MuseumEventElasticStatus status) {
        super();
        this.eventResponseDTO = eventResponseDTO;
        this.status = status;
    }
    
}
