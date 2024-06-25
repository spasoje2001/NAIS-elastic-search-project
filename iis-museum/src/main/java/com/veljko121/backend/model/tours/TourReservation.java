package com.veljko121.backend.model.tours;

import com.veljko121.backend.model.Guest;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tour_reservation")
public class TourReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Guest guest;

    @Column(nullable = false)
    private String numberOfAdultTickets;

    @Column(nullable = false)
    private String numberOfMinorTickets;

    @Column(nullable = false)
    private LocalDateTime reservationDateTime;

    @Column(nullable = false)
    private String totalPrice;

}
