package com.veljko121.backend.model.events;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.veljko121.backend.core.enums.EventInvitationStatus;
import com.veljko121.backend.model.Curator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Data
public class EventInvitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private MuseumEvent event;

    @ManyToOne
    private Curator curator;

    private EventInvitationStatus status;

    private String declinationExplanation;

    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    @Setter(value = AccessLevel.PRIVATE)
    private LocalDateTime createdDateTime;
    
}
