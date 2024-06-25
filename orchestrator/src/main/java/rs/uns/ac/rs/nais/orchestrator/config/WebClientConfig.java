package rs.uns.ac.rs.nais.orchestrator.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @Qualifier("exhibition")
    public WebClient exhibitionClient(@Value("${service.endpoints.exhibition}") String endpoint) {
        return WebClient.builder()
            .baseUrl(endpoint)
            .build();
    }
    
}
