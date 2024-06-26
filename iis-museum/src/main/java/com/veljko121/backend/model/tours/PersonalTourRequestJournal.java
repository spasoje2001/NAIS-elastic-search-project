package com.veljko121.backend.model.tours;

import com.veljko121.backend.core.enums.PersonalTourRequestStatus;
import com.veljko121.backend.model.Exhibition;
import com.veljko121.backend.model.Guest;
import com.veljko121.backend.model.Organizer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "personal_tour_request_journal")
public class PersonalTourRequestJournal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String operation;

    @NotEmpty
    @Column(nullable = false)
    private String guestNumber;

    @Column(nullable = false)
    private LocalDateTime occurrenceDateTime;

    @Column(nullable = false)
    private LocalDateTime dat;

    @NotEmpty
    @Column(nullable = false)
    private Integer personalTourRequestId;

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

    @NotEmpty
    @Column(nullable = false)
    private Integer vers;

}
