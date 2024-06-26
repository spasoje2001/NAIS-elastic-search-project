package rs.ac.uns.acs.nais.exhibition_service.service;

import rs.ac.uns.acs.nais.exhibition_service.core.service.ICRUDService;
import rs.ac.uns.acs.nais.exhibition_service.dto.OrganizerAverageRatingDTO;
import rs.ac.uns.acs.nais.exhibition_service.model.MuseumEvent;

import java.util.List;

public interface IEventService extends ICRUDService<MuseumEvent, String> {

    public List<OrganizerAverageRatingDTO> findAverageRatingByOrganizer(double minPrice, String searchText);

    void update(String id, MuseumEvent museumEvent);

    public List<MuseumEvent> findEventsByReviewTextAndDuration(String searchText, int minDuration);


}
