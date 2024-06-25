package rs.uns.ac.rs.nais.orchestrator.service.steps;

import reactor.core.publisher.Mono;
import rs.uns.ac.rs.nais.orchestrator.dto.ExhibitionRequestDTO;
import rs.uns.ac.rs.nais.orchestrator.dto.ExhibitionResponseDTO;
import rs.uns.ac.rs.nais.orchestrator.enums.ExhibitionStatus;
import rs.uns.ac.rs.nais.orchestrator.service.WorkflowStep;
import rs.uns.ac.rs.nais.orchestrator.service.WorkflowStepStatus;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class CreationStep implements WorkflowStep {

    private final WebClient webClient;
    private final ExhibitionRequestDTO requestDTO;
    private WorkflowStepStatus stepStatus = WorkflowStepStatus.PENDING;

    public CreationStep(WebClient webClient, ExhibitionRequestDTO requestDTO) {
        super();
        this.webClient = webClient;
        this.requestDTO = requestDTO;
    }

    @Override
    public WorkflowStepStatus getStatus() {
        return this.stepStatus;
    }

    @Override
    public Mono<Boolean> process() {
        return this.webClient
            .post()
            .uri("/exhibitions")
            .body(BodyInserters.fromValue(this.requestDTO))
            .retrieve()
            .bodyToMono(ExhibitionResponseDTO.class)
            .map(r -> r.getStatus().equals(ExhibitionStatus.COMPLETED))
            .doOnNext(b -> this.stepStatus = b ? WorkflowStepStatus.COMPLETE : WorkflowStepStatus.FAILED);
    }

    @Override
    public Mono<Boolean> revert() {
        return this.webClient
            .post()
            .uri("/exhibitions")
            .body(BodyInserters.fromValue(this.requestDTO))
            .retrieve()
            .bodyToMono(ExhibitionResponseDTO.class)
            .map(r -> r.getStatus().equals(ExhibitionStatus.COMPLETED))
            .doOnNext(b -> this.stepStatus = b ? WorkflowStepStatus.COMPLETE : WorkflowStepStatus.FAILED);
    }
    
}
