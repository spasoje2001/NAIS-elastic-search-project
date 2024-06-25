package com.veljko121.backend.model.tours;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tour_pricelist")
public class TourPricelist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(nullable = false)
    private String adultTicketPrice;

    @NotEmpty
    @Column(nullable = false)
    private String minorTicketPrice;

}
