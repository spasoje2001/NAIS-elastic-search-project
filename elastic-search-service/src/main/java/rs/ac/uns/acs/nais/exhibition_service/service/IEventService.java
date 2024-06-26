package rs.ac.uns.acs.nais.exhibition_service.service;

import rs.ac.uns.acs.nais.exhibition_service.core.service.ICRUDService;
import rs.ac.uns.acs.nais.exhibition_service.dto.OrganizerAverageRatingDTO;
import rs.ac.uns.acs.nais.exhibition_service.model.Event;

import java.util.List;

public interface IEventService extends ICRUDService<Event, String> {
    public List<OrganizerAverageRatingDTO> findAverageRatingByOrganizer(double minPrice, String searchText);

    public List<Event> findEventsByReviewTextAndDuration(String searchText, int minDuration);


}
