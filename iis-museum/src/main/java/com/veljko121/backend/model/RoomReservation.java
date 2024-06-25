package com.veljko121.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class RoomReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @NotNull
    private Room room;

    @Column
    private LocalDateTime startDateTime;

    @Column
    private LocalDateTime endDateTime; // Changed from durationMinutes to endDateTime

    /*

    VELJKO ZAKOMENTARISAO!
    Što se tiče rezervisanja prostorija, razmišljao sam malo o tome i palo mi je na pamet ovo rešenje (za 2 slučaja):
        - ako se rezerviše 1 prostorija (PREPORUKA): u okviru Exhibition se čuva samo room_reservation_id
        - ako se rezerviše VIŠE prostorija: u ovom slučaju može se napraviti još jedna međutabela (npr. ExhibitionRoomReservation) koja u sebi sadrži exhibition_id i room_reservation_id.
          Npr. za exhibition id=1 postoje room_reseravation id=1,2,3 i to se čuva u međutabeli (1, 1), (1, 2), (1, 3).
          NAPOMENA: Ovo jeste komplikovanije, ali jedino na ovaj način klasa RoomReservation ima samo podatke o tome kada i koja prostorija je dostupna, ništa više od toga (što i jeste poenta).
    
    */
    // @ManyToOne
    // @JoinColumn(name = "exhibition_id", nullable = true)
    // private Exhibition exhibition;



    /*

    VELJKO ZAKOMENTARISAO!
    Slična priča kao malopre, neka međutabela ExhibitionItemReservation sa exhibition_id i item_reservation_id.

    */
    // @OneToMany(mappedBy = "roomReservation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    // private List<ItemReservation> itemReservations;

}
