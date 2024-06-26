package rs.ac.uns.acs.nais.exhibition_service.config;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import rs.ac.uns.acs.nais.exhibition_service.events.eventRelationalDatabase.MuseumEventRelationalEvent;

@Configuration
public class EventHandlersConfig {

    @Autowired
    private MuseumEventStatusUpdateHandler eventStatusUpdateHandler;

    @Bean
    public Consumer<MuseumEventRelationalEvent> eventRelationalConsumer() {
        return pe -> {
            eventStatusUpdateHandler.updateEvent(pe.getEventResponseDTO().getId(), po -> {
                po.setRelationalStatus(pe.getStatus());
            });
        };
    }
    
}
