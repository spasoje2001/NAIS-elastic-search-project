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
    //@Query("{\"bool\":{\"filter\":[{\"range\":{\"price\":{\"gte\":?0}}}],\"must\":{\"match\":{\"reviews.text\":\"?1\"}}}}")
    //List<Event> findEventsByMinPriceAndReviewText(double minPrice, String searchText);

    @Query("{\"bool\":{\"filter\":[{\"range\":{\"price\":{\"gte\":?0}}}],\"must\":{\"match\":{\"reviews.text\":{\"query\":\"?1\",\"fuzziness\":\"AUTO\"}}}}}")
    List<Event> findEventsByMinPriceAndReviewText(double minPrice, String searchText);


    //@Query("{\"bool\":{\"filter\":[{\"range\":{\"durationMinutes\":{\"gte\":?1}}}],\"must\":{\"match\":{\"reviews.text\":\"?0\"}}}}")
    //List<Event> findEventsByReviewTextAndDuration(String searchText, int minDuration);

    @Query("{\"bool\":{\"filter\":[{\"range\":{\"durationMinutes\":{\"gte\":?1}}}],\"must\":{\"match\":{\"reviews.text\":{\"query\":\"?0\",\"fuzziness\":\"AUTO\"}}}}}")
    List<Event> findEventsByReviewTextAndDuration(String searchText, int minDuration);


}
