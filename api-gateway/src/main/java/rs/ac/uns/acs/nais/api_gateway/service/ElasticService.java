package rs.ac.uns.acs.nais.api_gateway.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import rs.ac.uns.acs.nais.api_gateway.model.Event;
import rs.ac.uns.acs.nais.api_gateway.model.EventElastic;

@Service
public class ElasticService {

    private final WebClient webClient;

    @Value("${elastic.host}") private String elasticHost;
    @Value("${elastic.port}") private String elasticPort;

    private String host = "";

    public ElasticService(WebClient.Builder webClientBuilder) {
        // var host = "http://" + elasticHost + ":" + elasticPort;
        host = "http://" + "elasticsearch-service" + ":" + "8080";
        this.webClient = webClientBuilder.baseUrl(host).build();
    }

    public Mono<Event> createEvent(EventElastic data) {
        System.out.println("ELASTICOOOOOO " + data);
        return webClient.post()
                .uri("/events")
                .bodyValue(data)
                .retrieve()
                .bodyToMono(Event.class);
    }
    
}
