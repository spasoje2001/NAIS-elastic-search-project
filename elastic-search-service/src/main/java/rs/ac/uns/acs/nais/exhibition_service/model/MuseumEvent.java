package rs.ac.uns.acs.nais.exhibition_service.model;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.acs.nais.exhibition_service.events.eventElasticSearchDatabase.MuseumEventElasticStatus;
import rs.ac.uns.acs.nais.exhibition_service.events.eventRelationalDatabase.MuseumEventRelationalStatus;

@Document(indexName = "event")
@Getter @Setter
public class MuseumEvent {

    @Id
    private String id;

    private String name;

    private String description;

    private Date startDateTime;

    private Integer durationMinutes;

    private Integer price;

    private Organizer organizer;

    private Collection<Review> reviews;

    private MuseumEventRelationalStatus relationalStatus;
    
    private MuseumEventElasticStatus elasticStatus;

    public Double getAverageRating() {
        Double averageRating = 0.;
        for (Review review : reviews) {
            averageRating = averageRating + review.getRating();
        }
        averageRating /= reviews.size();
        return averageRating;
    }

}
