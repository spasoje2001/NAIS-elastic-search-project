package rs.ac.uns.acs.nais.exhibition_service.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import rs.ac.uns.acs.nais.exhibition_service.model.Exhibition;

import java.util.List;
import java.util.Map;

@Repository
public interface ExhibitionRepository extends ElasticsearchRepository<Exhibition, String> {
    //@Query("{\"bool\": {\"must\": [{\"term\": {\"status.keyword\": \"OPEN\"}}, {\"range\": {\"ticketsSold\": {\"gt\": ?0}}} ]}}")
    //List<Exhibition> findOpenExhibitionsByMinTicketsSold(int minTicketsSold);

    @Query("{\"bool\": {" +
            "\"must\": [" +
            "{\"term\": {\"status.keyword\": \"OPEN\"}}," +  // Dodajemo filtriranje po statusu OPEN
            "{\"range\": {\"ticketsSold\": {\"gt\": ?0}}}," +  // Filtriranje po minimalnom broju prodatih karata
            "{\"match\": {\"theme\": \"?1\"}}" +  // Filtriranje po temi
            "]" +
            "}}")
    List<Exhibition> findOpenExhibitionsByMinTicketsSold(int minTicketsSold, String theme);

    @Query("""
    {
      "bool": {
        "must": [
          { "match": { "longDescription": "?0" } },
          { "range": { "startDate": { "gte": "?1", "lte": "?2" } } }
        ],
        "filter": [
          { "range": { "ticketsSold": { "gte": ?3 } } },
          { "term": { "status.keyword": "OPEN" } }
        ]
      }
    }
    """)
    List<Exhibition> findByDescriptionAndDateRangeAndMinTicketsSold(
            String searchText,
            String minStartDate,
            String maxStartDate,
            int minTicketsSold
    );

    @Query("""
    {
      "bool": {
        "must": [
          { "match": { "reviews.text": "?0" } },
          { "term": { "theme.keyword": "?1" } }
        ],
        "filter": [
          { "term": { "status.keyword": "OPEN" } }
        ]
      }
    }
    """)
    List<Exhibition> findByReviewTextAndTheme(
            String reviewText,
            String theme
    );

    @Query("""
    {
      "bool": {
        "must": [
          { "match": { "items.period": "?0" } }
        ],
        "filter": [
          { "range": { "ticketsSold": { "gte": ?1 } } },
          { "term": { "status.keyword": "?2" } }
        ]
      }
    }
    """)
    List<Exhibition> findByPeriodTextAndMinTicketsSoldAndStatus(
            String periodText,
            int minTicketsSold,
            String status
    );

    @Query("""
    {
        "bool": {
          "must": [
            { "match": { "items.category.keyword": { "query": "?0" } } },
            { "match": { "items.description": { "query": "?1" } } }
          ],
          "filter": {
            "term": { "status.keyword": "?2" }
          }
        }
    }
    """)
    List<Exhibition> findByCategoryAndItemDescriptionAndStatus(String category, String description, String status);

}
