package rs.ac.uns.acs.nais.exhibition_service.service.impl;

import org.springframework.stereotype.Service;

import rs.ac.uns.acs.nais.exhibition_service.core.service.impl.CRUDService;
import rs.ac.uns.acs.nais.exhibition_service.model.Exhibition;
import rs.ac.uns.acs.nais.exhibition_service.repository.ExhibitionRepository;
import rs.ac.uns.acs.nais.exhibition_service.service.IExhibitionService;

@Service
public class ExhibitionService extends CRUDService<Exhibition, String> implements IExhibitionService {
    
    public ExhibitionService(ExhibitionRepository exhibitionRepository) {
        super(exhibitionRepository);
    }

}
