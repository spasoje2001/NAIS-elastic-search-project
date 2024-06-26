package rs.ac.uns.acs.nais.exhibition_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks;
import rs.ac.uns.acs.nais.exhibition_service.dto.EventResponseDTO;
import rs.ac.uns.acs.nais.exhibition_service.events.eventElasticSearchDatabase.MuseumEventElasticEvent;
import rs.ac.uns.acs.nais.exhibition_service.events.eventElasticSearchDatabase.MuseumEventElasticStatus;
import rs.ac.uns.acs.nais.exhibition_service.model.MuseumEvent;

@Service
public class EventStatusPublisher {

    @Autowired
    private Sinks.Many<MuseumEventElasticEvent> eventSink;

    @Autowired
    private ModelMapper modelMapper;

    public void raiseMuseumEventEvent(final MuseumEvent event, MuseumEventElasticStatus eventStatus){
        var dto = modelMapper.map(event, EventResponseDTO.class);
        var eventEvent = new MuseumEventElasticEvent(dto, eventStatus);
        this.eventSink.tryEmitNext(eventEvent);
    }

}
