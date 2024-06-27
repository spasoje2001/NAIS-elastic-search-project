package com.veljko121.backend.model.tours;

import com.veljko121.backend.core.enums.PersonalTourRequestStatus;
import com.veljko121.backend.model.Guest;
import com.veljko121.backend.model.Exhibition;
import com.veljko121.backend.model.Organizer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "personal_tour_request")
public class PersonalTourRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime occurrenceDateTime;

    @NotEmpty
    @Column(nullable = false)
    private String guestNumber;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH})
    @JoinTable(name = "personal_tour_requests_exhibitions",
            joinColumns = @JoinColumn(name = "personal_tour_request_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "exhibition_id", referencedColumnName = "id"))
    @ToString.Exclude
    private List<Exhibition> exhibitions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "proposer_id")
    private Guest proposer;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) default 'ON_HOLD'")
    private PersonalTourRequestStatus status;

    @Column()
    private String denialReason;

    @NotEmpty
    @Column(nullable = false)
    private String proposerContactPhone;

}
