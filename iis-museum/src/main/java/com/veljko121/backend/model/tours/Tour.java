package com.veljko121.backend.model.tours;

import com.veljko121.backend.core.enums.TourCategory;
import com.veljko121.backend.model.Curator;
import com.veljko121.backend.model.Exhibition;
import com.veljko121.backend.model.Organizer;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "tour")
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NotEmpty
    @Column(nullable = false)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(name = "tours_exhibitions", joinColumns = @JoinColumn(name = "tour_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "exhibition_id", referencedColumnName = "id"))
    @ToString.Exclude
    private List<Exhibition> exhibitions = new ArrayList<>();

    @NotEmpty
    @Column(nullable = false)
    private String duration;

    @Column(nullable = false)
    private LocalDateTime occurrenceDateTime;

    @NotEmpty
    @Column(nullable = false)
    private String adultTicketPrice;

    @NotEmpty
    @Column(nullable = false)
    private String minorTicketPrice;

    @ManyToOne
    @JoinColumn(name = "guide_id")
    private Curator guide;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;

    @NotEmpty
    @Column(nullable = false)
    private String picturePath;

    @NotEmpty
    @Column(nullable = false)
    private String capacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20)")
    private TourCategory category;

}
