package rs.ac.uns.acs.nais.exhibition_service.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import rs.ac.uns.acs.nais.exhibition_service.model.Exhibition;

import java.util.List;

@Repository
public interface ExhibitionRepository extends ElasticsearchRepository<Exhibition, String> {

    //@Query("{\"bool\": {\"must\": [{\"match\": {\"status.keyword\": \"OPEN\"}}, {\"range\": {\"ticketsSold\": {\"gt\": 5000}}}]}, \"sort\": [{\"ticketsSold\": {\"order\": \"desc\"}}]}")

    //@Query("{\"term\": {\"status.keyword\": \"OPEN\"}}")
    //@Query("{\"bool\": {\"must\": [{\"term\": {\"status.keyword\": \"OPEN\"}}, {\"range\": {\"ticketsSold\": {\"gt\": 10000}}}]}}")
    @Query("{\"bool\": {\"must\": [{\"term\": {\"status.keyword\": \"OPEN\"}}, {\"range\": {\"ticketsSold\": {\"gt\": ?0}}} ]}}")
    List<Exhibition> findOpenExhibitionsWithHighAttendance(int minTicketsSold);

}
