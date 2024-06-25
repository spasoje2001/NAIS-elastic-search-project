package rs.ac.uns.acs.nais.exhibition_service.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import rs.ac.uns.acs.nais.exhibition_service.enums.ExhibitionStatus;
import rs.ac.uns.acs.nais.exhibition_service.enums.ExhibitionTheme;
import rs.ac.uns.acs.nais.exhibition_service.model.*;

import java.util.Collection;
import java.util.Date;

@Getter @Setter
public class ExhibitionResponseDTO {
    private String id;
    private String name;
    private String shortDescription;
    private String longDescription;
    private ExhibitionTheme theme;
    private ExhibitionStatus status;
    private Date startDate;
    private Date endDate;
    private Integer price;
    private Integer ticketsSold;
    private Organizer organizer;
    private Curator curator;
    private Room room;
    private Collection<Item> items;
    private Collection<Review> reviews;
}
