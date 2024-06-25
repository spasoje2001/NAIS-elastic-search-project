package rs.ac.uns.acs.nais.exhibition_service.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import rs.ac.uns.acs.nais.exhibition_service.enums.ExhibitionStatus;
import rs.ac.uns.acs.nais.exhibition_service.enums.ExhibitionTheme;
import rs.ac.uns.acs.nais.exhibition_service.model.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

@Getter @Setter
public class ExhibitionRequestDTO {
    private String name;
    private String shortDescription;
    private String longDescription;
    private ExhibitionTheme theme;
    private ExhibitionStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer price;
    private Organizer organizer;
    private Curator curator;
    private Room room;
}
