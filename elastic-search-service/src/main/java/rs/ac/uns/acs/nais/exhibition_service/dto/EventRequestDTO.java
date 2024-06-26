package rs.ac.uns.acs.nais.exhibition_service.dto;

import java.time.LocalDate;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.acs.nais.exhibition_service.model.Organizer;
import rs.ac.uns.acs.nais.exhibition_service.model.Room;

@Getter @Setter
public class EventRequestDTO {

    private String name;
    private String description;
    private LocalDate startDateTime;
    private Integer durationMinutes;
    private Integer price;
    private Organizer organizer;
    private Room room;

}
