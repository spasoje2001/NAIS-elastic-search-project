package com.veljko121.backend.config;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.veljko121.backend.events.eventElasticSearchDatabase.MuseumElasticEvent;
import com.veljko121.backend.events.eventElasticSearchDatabase.MuseumEventElasticStatus;
import com.veljko121.backend.events.eventRelationalDatabase.MuseumEventRelationalStatus;
import com.veljko121.backend.events.eventRelationalDatabase.MuseumRelationalEvent;
import com.veljko121.backend.service.events.IEventService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class EventRelationalConfig {

    @Autowired
    private IEventService eventService;

    @Bean
    public Function<Flux<MuseumElasticEvent>, Flux<MuseumRelationalEvent>> eventRelationalProcessor() {
        return flux -> flux.flatMap(this::process);
    }

    private Mono<MuseumRelationalEvent> process(MuseumElasticEvent event) {
        if (event.getStatus().equals(MuseumEventElasticStatus.CREATED)) {
            return Mono.fromSupplier(() -> fromElasticEvent(event));
        }
        else {
            return Mono.fromRunnable(() -> this.eventService.deleteById(event.getEventResponseDTO().getId()));
        }
    }

    private MuseumRelationalEvent fromElasticEvent(MuseumElasticEvent event) {
        return new MuseumRelationalEvent(event.getEventResponseDTO(), MuseumEventRelationalStatus.CREATED);
    }
    
}
