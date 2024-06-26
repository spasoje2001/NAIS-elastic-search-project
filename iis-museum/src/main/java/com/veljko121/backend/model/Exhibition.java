package com.veljko121.backend.model;

import com.veljko121.backend.core.enums.ExhibitionStatus;
import com.veljko121.backend.core.enums.ExhibitionTheme;
import com.veljko121.backend.model.tours.PersonalTour;
import com.veljko121.backend.model.tours.PersonalTourRequest;
import com.veljko121.backend.model.tours.Tour;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.ToString;
import org.hibernate.Remove;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Exhibition {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String picture;

    @Column
    private String shortDescription;

    @Column
    private String longDescription;

    @Enumerated(EnumType.STRING)
    private ExhibitionTheme theme;

    @Enumerated(EnumType.STRING)
    private ExhibitionStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @PositiveOrZero
    @Column(nullable = false)
    private Integer price; // The price in whole euros

    @PositiveOrZero
    @Column(nullable = false)
    private Integer ticketsSold; // The price in whole euros

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;

    @ManyToOne
    @JoinColumn(name = "curator_id")
    private Curator curator;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "room_reservation_id")
    private RoomReservation roomReservation;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "exhibition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemReservation> itemReservations = new ArrayList<>();

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(name = "tours_exhibitions", joinColumns = @JoinColumn(name = "tour_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "exhibition_id", referencedColumnName = "id"))
    @ToString.Exclude
    private List<Tour> tours = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(name = "personal_tours_exhibitions",
            joinColumns = @JoinColumn(name = "personal_tour_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "exhibition_id", referencedColumnName = "id"))
    @ToString.Exclude
    private List<PersonalTour> personalTours = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH})
    @JoinTable(name = "personal_tour_requests_exhibitions",
            joinColumns = @JoinColumn(name = "personal_tour_request_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "exhibition_id", referencedColumnName = "id"))
    @ToString.Exclude
    private List<PersonalTourRequest> personalTourRequests = new ArrayList<>();

    public boolean isPermanent() {
        return endDate == null;
    }

    public boolean isTemporary() {
        return endDate != null;
    }

    public boolean isOngoing() {
        Date currentDate = new Date();
        // The exhibition is ongoing if it has started and either has no end date (permanent) or hasn't ended yet (temporary).
        return !currentDate.before(startDate) && (endDate == null || currentDate.before(endDate));
    }

    public Double getRevenue() {
        return (double)price*ticketsSold;
    }

    public boolean isFree() {
        return price == null || price == 0;
    }

}
