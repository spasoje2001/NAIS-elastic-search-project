package rs.ac.uns.acs.nais.exhibition_service.service.impl;

import org.springframework.stereotype.Service;

import rs.ac.uns.acs.nais.exhibition_service.core.service.impl.CRUDService;
import rs.ac.uns.acs.nais.exhibition_service.dto.OrganizerAverageRatingDTO;
import rs.ac.uns.acs.nais.exhibition_service.model.Event;
import rs.ac.uns.acs.nais.exhibition_service.repository.EventRepository;
import rs.ac.uns.acs.nais.exhibition_service.service.IEventService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class EventService extends CRUDService<Event, String> implements IEventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        super(eventRepository);
        this.eventRepository = eventRepository;
    }

    public List<OrganizerAverageRatingDTO> findAverageRatingByOrganizer(double minPrice, String searchText) {
        List<Event> events = eventRepository.findEventsByMinPriceAndReviewText(minPrice, searchText);
        Map<String, OrganizerAverageRatingDTO> organizerRatingMap = new HashMap<>();

        for (Event event : events) {
            String organizerKey = event.getOrganizer().getFirstName() + " " + event.getOrganizer().getLastName();
            OrganizerAverageRatingDTO organizerRating = organizerRatingMap.computeIfAbsent(organizerKey, k -> {
                OrganizerAverageRatingDTO dto = new OrganizerAverageRatingDTO();
                dto.setOrganizerFirstName(event.getOrganizer().getFirstName());
                dto.setOrganizerLastName(event.getOrganizer().getLastName());
                dto.setTotalReviews(0);
                dto.setAverageRating(0.0);
                return dto;
            });

            int totalReviews = event.getReviews().size();
            double averageRating = event.getReviews().stream().mapToInt(r -> r.getRating()).average().orElse(0.0);

            organizerRating.setTotalReviews(organizerRating.getTotalReviews() + totalReviews);
            organizerRating.setAverageRating(organizerRating.getAverageRating() + (averageRating * totalReviews));
        }

        for (OrganizerAverageRatingDTO ratingDTO : organizerRatingMap.values()) {
            if (ratingDTO.getTotalReviews() > 0) {
                double finalAverage = ratingDTO.getAverageRating() / ratingDTO.getTotalReviews();
                BigDecimal bd = BigDecimal.valueOf(finalAverage).setScale(2, RoundingMode.HALF_UP);
                ratingDTO.setAverageRating(bd.doubleValue());
            } else {
                ratingDTO.setAverageRating(0.0);
            }
        }

        return new ArrayList<>(organizerRatingMap.values());
    }

    public List<Event> findEventsByReviewTextAndDuration(String searchText, int minDuration) {
        List<Event> events = eventRepository.findEventsByReviewTextAndDuration(searchText, minDuration);
        events.sort(Comparator.comparingDouble(Event::getPrice));

        return events;
    }


}
