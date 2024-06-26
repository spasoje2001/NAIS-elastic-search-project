package com.veljko121.backend.dto.tours;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TourReservationCreateDTO {

    private Integer tourId;

    private String numberOfAdultTickets;

    private String numberOfMinorTickets;

}
