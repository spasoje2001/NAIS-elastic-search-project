package rs.ac.uns.acs.nais.exhibition_service.config;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import rs.ac.uns.acs.nais.exhibition_service.events.eventElasticSearchDatabase.MuseumEventElasticEvent;

@Configuration
public class SinkFluxConfig {

    // @Bean
    // public Sinks.Many<MuseumEventElasticEvent> eventSink(){
    //     return Sinks.many().unicast().onBackpressureBuffer();
    // }

    // @Bean
    // public Supplier<Flux<MuseumEventElasticEvent>> eventSupplier(Sinks.Many<MuseumEventElasticEvent> sink){
    //     return sink::asFlux;
    // }
    
}
