package com.veljko121.backend.dto;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.annotation.Id;

import com.veljko121.backend.events.eventElasticSearchDatabase.MuseumEventElasticStatus;
import com.veljko121.backend.events.eventRelationalDatabase.MuseumEventRelationalStatus;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MuseumEventForElasticDTO {

    @Id
    private String id;

    private String name;

    private String description;

    private Date startDateTime;

    private Integer durationMinutes;

    private Integer price;

    private OrganizerForElasticDTO organizer;

    private Collection<Review> reviews;

    private MuseumEventRelationalStatus relationalStatus;
    
    private MuseumEventElasticStatus elasticStatus;

}
