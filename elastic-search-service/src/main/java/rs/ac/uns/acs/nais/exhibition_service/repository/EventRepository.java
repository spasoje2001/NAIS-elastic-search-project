package rs.ac.uns.acs.nais.exhibition_service.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import rs.ac.uns.acs.nais.exhibition_service.model.MuseumEvent;

import java.util.List;
import java.util.Map;

@Repository
public interface EventRepository extends ElasticsearchRepository<MuseumEvent, String> {

    @Query("{\"bool\":{\"filter\":[{\"range\":{\"price\":{\"gte\":?0}}}],\"must\":{\"match\":{\"reviews.text\":{\"query\":\"?1\",\"fuzziness\":\"AUTO\"}}}}}")
    List<MuseumEvent> findEventsByMinPriceAndReviewText(double minPrice, String searchText);

    @Query("{\"bool\":{\"filter\":[{\"range\":{\"durationMinutes\":{\"gte\":?1}}}],\"must\":{\"match\":{\"reviews.text\":{\"query\":\"?0\",\"fuzziness\":\"AUTO\"}}}}}")
    List<MuseumEvent> findEventsByReviewTextAndDuration(String searchText, int minDuration);

}
