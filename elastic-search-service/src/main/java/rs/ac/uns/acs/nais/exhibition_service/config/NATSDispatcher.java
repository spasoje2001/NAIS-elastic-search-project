package rs.ac.uns.acs.nais.exhibition_service.config;

import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import lombok.RequiredArgsConstructor;
import rs.ac.uns.acs.nais.exhibition_service.model.MuseumEvent;
import rs.ac.uns.acs.nais.exhibition_service.service.IEventService;

@Configuration
@RequiredArgsConstructor
public class NATSDispatcher {
    
    private final Connection natsConnection;

    private final IEventService eventService;

    @Bean
    public Dispatcher museumEventDispatcher() {
    System.out.println("DISPATCHER WORKS!");
        var dispatcher = natsConnection.createDispatcher(msg -> {
            var data = new String(msg.getData(), StandardCharsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                MuseumEvent museumEvent = objectMapper.readValue(data, MuseumEvent.class);
                eventService.save(museumEvent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        dispatcher.subscribe("museum-events");
        return dispatcher;
    }

}
