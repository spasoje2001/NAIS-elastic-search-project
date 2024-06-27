package rs.ac.uns.acs.nais.api_gateway.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RoomReservationResponseDTO {

    private Integer id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private RoomResponseDTO room;
    
}
