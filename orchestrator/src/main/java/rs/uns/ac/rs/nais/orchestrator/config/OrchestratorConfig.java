package rs.uns.ac.rs.nais.orchestrator.config;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Flux;
import rs.uns.ac.rs.nais.orchestrator.dto.ExhibitionRequestDTO;
import rs.uns.ac.rs.nais.orchestrator.dto.ExhibitionResponseDTO;
import rs.uns.ac.rs.nais.orchestrator.service.OrchestratorService;

@Configuration
public class OrchestratorConfig {

    @Autowired
    private OrchestratorService orchestratorService;

    @Bean
    public Function<Flux<ExhibitionRequestDTO>, Flux<ExhibitionResponseDTO>> processor(){
        return flux -> flux
                        .flatMap(dto -> this.orchestratorService.createExhibition(dto))
                        .doOnNext(dto -> System.out.println("Status : " + dto.getStatus()));
    }

}
