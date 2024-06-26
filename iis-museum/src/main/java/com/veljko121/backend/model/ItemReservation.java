package com.veljko121.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ItemReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @NotNull
    private Item item;

    @Column
    private LocalDateTime startDateTime;

    @Column
    private LocalDateTime endDateTime; // Changed from durationMinutes to endDateTime

    @ManyToOne
    @JoinColumn(name = "exhibition_id")
    private Exhibition exhibition;

}
