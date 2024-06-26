package rs.ac.uns.acs.nais.exhibition_service.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.ac.uns.acs.nais.exhibition_service.config.EventStatusPublisher;
import rs.ac.uns.acs.nais.exhibition_service.core.service.impl.CRUDService;
import rs.ac.uns.acs.nais.exhibition_service.events.eventElasticSearchDatabase.MuseumEventElasticStatus;
import rs.ac.uns.acs.nais.exhibition_service.model.MuseumEvent;
import rs.ac.uns.acs.nais.exhibition_service.repository.EventRepository;
import rs.ac.uns.acs.nais.exhibition_service.service.IEventService;

@Service
public class EventService extends CRUDService<MuseumEvent, String> implements IEventService {

    private EventStatusPublisher eventStatusPublisher;

    public EventService(EventRepository eventRepository) {
        super(eventRepository);
    }

    @Override
    @Transactional
    public MuseumEvent save(MuseumEvent entity) {
        var event = super.save(entity);
        eventStatusPublisher.raiseMuseumEventEvent(entity, MuseumEventElasticStatus.CREATED);
        return event;
    }
    
}
