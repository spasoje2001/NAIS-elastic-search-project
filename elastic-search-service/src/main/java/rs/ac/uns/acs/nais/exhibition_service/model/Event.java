package rs.ac.uns.acs.nais.exhibition_service.model;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Getter;
import lombok.Setter;

@Document(indexName = "event")
@Getter @Setter
public class Event {

    @Id
    private String id;

    private String name;

    private String description;

    private Date startDateTime;

    private Integer durationMinutes;

    private Integer price;

    private Organizer organizer;

    private Room room;

    private Collection<Review> reviews;

    public Double getAverageRating() {
        Double averageRating = 0.;
        for (Review review : reviews) {
            averageRating = averageRating + review.getRating();
        }
        averageRating /= reviews.size();
        return averageRating;
    }

}
