package rs.ac.uns.acs.nais.exhibition_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrganizerAverageRatingDTO {
    private String organizerFirstName;
    private String organizerLastName;
    private Double averageRating;
    private int totalReviews;
}
