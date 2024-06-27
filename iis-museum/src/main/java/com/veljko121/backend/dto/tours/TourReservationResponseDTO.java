package com.veljko121.backend.dto.tours;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TourReservationResponseDTO {

    private Integer id;

    private Integer tourId;

    private Integer guestId;

    private String numberOfAdultTickets;

    private String numberOfMinorTickets;

    private LocalDateTime reservationDateTime;

    private String totalPrice;

}
