package rs.ac.uns.acs.nais.exhibition_service.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.acs.nais.exhibition_service.model.Organizer;
import rs.ac.uns.acs.nais.exhibition_service.model.Room;

@Getter @Setter
public class EventResponseDTO {

    private String id;
    private String name;
    private String description;
    private Date startDateTime;
    private Integer durationMinutes;
    private Integer price;
    private Organizer organizer;
    private Room room;
    
}
