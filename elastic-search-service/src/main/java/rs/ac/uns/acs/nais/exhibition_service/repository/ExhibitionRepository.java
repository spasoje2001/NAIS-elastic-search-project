package rs.ac.uns.acs.nais.exhibition_service.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import rs.ac.uns.acs.nais.exhibition_service.model.Exhibition;

@Repository
public interface ExhibitionRepository extends ElasticsearchRepository<Exhibition, String> {
    
}
