package rs.ac.uns.acs.nais.exhibition_service.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import rs.ac.uns.acs.nais.exhibition_service.model.MuseumEvent;

@Repository
public interface EventRepository extends ElasticsearchRepository<MuseumEvent, String> {
    
}
