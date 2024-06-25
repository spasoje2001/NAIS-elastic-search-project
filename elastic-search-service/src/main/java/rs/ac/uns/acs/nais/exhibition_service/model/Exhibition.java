package rs.ac.uns.acs.nais.exhibition_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import rs.ac.uns.acs.nais.exhibition_service.enums.ExhibitionStatus;
import rs.ac.uns.acs.nais.exhibition_service.enums.ExhibitionTheme;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Document(indexName = "exhibition")
@Getter @Setter
public class Exhibition {

    @Id
    private String id;

    private String name;

    private String shortDescription;

    private String longDescription;

    private ExhibitionTheme theme;

    private ExhibitionStatus status;

    @Field(type = FieldType.Date)
    private Date startDate;

    @Field(type = FieldType.Date)
    private Date endDate;

    private Integer price; // The price in whole euros

    private Integer ticketsSold; // The price in whole euros

    private Organizer organizer;

    private Curator curator;

    private Room room;

    private Collection<Item>  items;

    private Collection<Review> reviews;

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

    public Double getAverageRating() {
        Double averageRating = 0.;
        for (Review review : reviews) {
            averageRating = averageRating + review.getRating();
        }
        averageRating /= reviews.size();
        return averageRating;
    }





}
