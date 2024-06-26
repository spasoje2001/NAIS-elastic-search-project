package com.veljko121.backend.dto.tours;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TourPricelistCreateDTO {

    private String adultTicketPrice;

    private String minorTicketPrice;

}
