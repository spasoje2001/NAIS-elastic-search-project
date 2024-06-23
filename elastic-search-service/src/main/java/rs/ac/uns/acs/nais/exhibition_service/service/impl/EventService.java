package rs.ac.uns.acs.nais.exhibition_service.service.impl;

import org.springframework.stereotype.Service;

import rs.ac.uns.acs.nais.exhibition_service.core.service.impl.CRUDService;
import rs.ac.uns.acs.nais.exhibition_service.model.Event;
import rs.ac.uns.acs.nais.exhibition_service.repository.EventRepository;
import rs.ac.uns.acs.nais.exhibition_service.service.IEventService;

@Service
public class EventService extends CRUDService<Event, String> implements IEventService {

    public EventService(EventRepository eventRepository) {
        super(eventRepository);
    }
    
}
