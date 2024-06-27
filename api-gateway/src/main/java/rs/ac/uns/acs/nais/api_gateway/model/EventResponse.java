package rs.ac.uns.acs.nais.api_gateway.model;

import java.time.LocalDateTime;
import java.util.Collection;

import lombok.Data;
import rs.ac.uns.acs.nais.api_gateway.enums.EventStatus;

@Data
public class EventResponse {

    private Integer id;
    private String name;
    private String description;
    private LocalDateTime startDateTime;
    private Integer durationMinutes;
    private Integer ticketsNumber;
    private Integer price;
    private OrganizerResponse organizer;
    private LocalDateTime createdDateTime;
    private RoomReservationResponseDTO roomReservation;
    private EventStatus status;
    private Collection<EventPictureResponseDTO> pictures;

}
