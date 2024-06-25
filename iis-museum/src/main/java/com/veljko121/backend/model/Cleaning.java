package com.veljko121.backend.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.veljko121.backend.core.enums.CleaningStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
@Table(name = "cleaning")
public class Cleaning {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDate startDate;


    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated
    private CleaningStatus status;

    @ManyToOne
    @JoinColumn(name = "curator_id")
    private Curator curator;

    @ManyToOne
    @JoinColumn(name = "restaurateur_id")
    private Restaurateur restaurateur;

    @Column(name = "item_id")
    private Integer itemId;

    @Column()
    private String denialReason;

    @Column()
    private LocalDate putToCleaningTime;

    @Column()
    private LocalDate finishCleaningTime;
}
