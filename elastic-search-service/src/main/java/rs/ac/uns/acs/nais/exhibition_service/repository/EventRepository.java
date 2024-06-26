package rs.ac.uns.acs.nais.exhibition_service.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import rs.ac.uns.acs.nais.exhibition_service.dto.OrganizerAverageRatingDTO;
import rs.ac.uns.acs.nais.exhibition_service.model.Event;

import java.util.List;
import java.util.Map;

@Repository
public interface EventRepository extends ElasticsearchRepository<Event, String> {

    //@Query("{\"aggs\": {\"organizer_avg_rating\": {\"terms\": {\"field\": \"organizer.firstName.keyword\"}, \"aggs\": {\"avg_rating\": {\"avg\": {\"field\": \"reviews.rating\"}}}}, \"query\": {\"range\": {\"price\": {\"gte\": \"?0\"}}}}")
    //List<Map<String, Object>> findAverageRatingByOrganizer(double minPrice);

    //@Query("{\"bool\":{\"filter\":[{\"range\":{\"price\":{\"gte\":?0}}}]}}")
    //List<Event> findEventsByMinPrice(double minPrice);

    @Query("{\"bool\":{\"filter\":[{\"range\":{\"price\":{\"gte\":?0}}}],\"must\":{\"match\":{\"reviews.text\":\"?1\"}}}}")
    List<Event> findEventsByMinPriceAndReviewText(double minPrice, String searchText);

}
