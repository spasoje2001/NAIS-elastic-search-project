package com.veljko121.backend.model.events;

import java.time.LocalDateTime;
import java.util.Collection;

import org.hibernate.annotations.CreationTimestamp;

import com.veljko121.backend.core.enums.EventStatus;
import com.veljko121.backend.model.Organizer;
import com.veljko121.backend.model.RoomReservation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotEmpty
    @Column(nullable = false)
    private String name;
    
    @Column(length = 2048)
    private String description;
    
    @NotNull
    @Column(nullable = false)
    private LocalDateTime startDateTime;
    
    @NotNull
    @Column(nullable = false)
    private Integer durationMinutes; // in minutes
    
    @Positive
    @Column(nullable = false)
    private Integer ticketsNumber;
    
    @PositiveOrZero
    @Column(nullable = false)
    private Integer price;
    
    @ManyToOne
    @NotNull
    private Organizer organizer;

    @OneToOne
    private RoomReservation roomReservation;

    @Enumerated
    private EventStatus status;

    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    @Setter(value = AccessLevel.PRIVATE)
    private LocalDateTime createdDateTime;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    private Collection<EventPicture> pictures;

    public LocalDateTime getEndDateTime() {
        return startDateTime.plusMinutes(durationMinutes);
    }

}
