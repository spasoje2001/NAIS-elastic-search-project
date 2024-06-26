package rs.ac.uns.acs.nais.exhibition_service.config;

import java.util.Objects;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.ac.uns.acs.nais.exhibition_service.events.eventElasticSearchDatabase.MuseumEventElasticStatus;
import rs.ac.uns.acs.nais.exhibition_service.events.eventRelationalDatabase.MuseumEventRelationalStatus;
import rs.ac.uns.acs.nais.exhibition_service.model.MuseumEvent;
import rs.ac.uns.acs.nais.exhibition_service.repository.EventRepository;

@Service
public class MuseumEventStatusUpdateHandler {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventStatusPublisher eventStatusPublisher;

    @Transactional
    public void updateEvent(final String id, Consumer<MuseumEvent> consumer) {
        this.eventRepository.findById(id).ifPresent(consumer.andThen(this::updateEvent));
    }

    private void updateEvent(MuseumEvent event) {
        if (Objects.isNull(event.getElasticStatus())) return;

        var isComplete = MuseumEventRelationalStatus.CREATED.equals(event.getRelationalStatus());
        var museumEventStatus = isComplete ? MuseumEventElasticStatus.CREATED : MuseumEventElasticStatus.REJECTED;
        event.setElasticStatus(museumEventStatus);

        if (!isComplete) {
            this.eventStatusPublisher.raiseMuseumEventEvent(event, museumEventStatus);
        }
    }
    
}
