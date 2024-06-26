package rs.ac.uns.acs.nais.api_gateway.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EventElastic {

    private String name;
    private String description;
    
    private LocalDate startDateTime;
    
    private Integer durationMinutes;
    private Integer price;
    private Organizer organizer;
    private Room room;
}
