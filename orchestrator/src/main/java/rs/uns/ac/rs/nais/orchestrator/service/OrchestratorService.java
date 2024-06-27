package rs.uns.ac.rs.nais.orchestrator.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rs.uns.ac.rs.nais.orchestrator.dto.ExhibitionRequestDTO;
import rs.uns.ac.rs.nais.orchestrator.dto.ExhibitionResponseDTO;
import rs.uns.ac.rs.nais.orchestrator.enums.EventSAGAStatus;
import rs.uns.ac.rs.nais.orchestrator.service.steps.CreationStep;

@Service
public class OrchestratorService {

    @Autowired
    @Qualifier("exhibition")
    private WebClient exhibitionClient;

    @Autowired
    private ModelMapper modelMapper;

    public Mono<ExhibitionResponseDTO> createExhibition(final ExhibitionRequestDTO requestDTO) {
        Workflow exhibitionWorkflow = this.getExhibitionWorkflow(requestDTO);
        return Flux.fromStream(() -> exhibitionWorkflow.getSteps().stream()).
            flatMap(WorkflowStep::process)
            .handle(((aBoolean, synchronousSink) -> {
                if(aBoolean)
                    synchronousSink.next(true);
                else
                    synchronousSink.error(new WorkflowException("create exhibition failed!"));
            }))
            .then(Mono.fromCallable(() -> getResponseDTO(requestDTO, EventSAGAStatus.COMPLETED)))
            .onErrorResume(ex -> this.revertOrder(exhibitionWorkflow, requestDTO));
    }

    private Mono<ExhibitionResponseDTO> revertOrder(final Workflow workflow, final ExhibitionRequestDTO requestDTO) {
        return Flux.fromStream(() -> workflow.getSteps().stream())
                .filter(wf -> wf.getStatus().equals(WorkflowStepStatus.COMPLETE))
                .flatMap(WorkflowStep::revert)
                .retry(3)
                .then(Mono.just(this.getResponseDTO(requestDTO, EventSAGAStatus.CANCELLED)));
    }

    private Workflow getExhibitionWorkflow(ExhibitionRequestDTO requestDTO) {
        var creationStep = new CreationStep(this.exhibitionClient, requestDTO);
        return new ExhibitionWorkflow(List.of(creationStep));
    }

    private ExhibitionResponseDTO getResponseDTO(ExhibitionRequestDTO requestDTO, EventSAGAStatus status) {
        var dto = modelMapper.map(requestDTO, ExhibitionResponseDTO.class);
        dto.setStatus(status);
        return dto;
    }
    
}
