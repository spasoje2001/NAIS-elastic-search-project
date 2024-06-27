package rs.ac.uns.acs.nais.api_gateway.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import rs.ac.uns.acs.nais.api_gateway.model.Event;
import rs.ac.uns.acs.nais.api_gateway.model.EventResponse;

@Service
public class RelationalService {

    private final WebClient webClient;

    @Value("${relational.host}") private String relationalHost;

    
    @Value("${relational.port}") private String relationalPort;

    private String host = "";

    public RelationalService(WebClient.Builder webClientBuilder) {
        // host = "http://" + relationalHost + ":" + relationalPort;
        host = "http://" + "iis-museum" + ":" + "8081";
        this.webClient = webClientBuilder.baseUrl(host).build();
    }
    
    public Mono<EventResponse> createEvent(Event data) {
        System.out.println("RELACIONOOOOO " + data);
        System.out.println("RELACIONI HOST " + host);
        return webClient.post()
                .uri("/api/events")
                .bodyValue(data)
                .retrieve()
                .bodyToMono(EventResponse.class);
    }
    
    public Mono<Void> compensateCreateEvent(Integer eventId) {
        return webClient.delete()
                .uri("/api/events/{itemId}", eventId)
                .retrieve()
                .bodyToMono(Void.class);
    }

}
