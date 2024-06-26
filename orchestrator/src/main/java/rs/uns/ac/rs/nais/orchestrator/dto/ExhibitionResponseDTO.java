package rs.uns.ac.rs.nais.orchestrator.dto;

import lombok.Getter;
import lombok.Setter;
import rs.uns.ac.rs.nais.orchestrator.enums.EventSAGAStatus;

@Getter @Setter
public class ExhibitionResponseDTO {

    private String id;
    private String name;
    private String shortDescription;
    private String longDescription;
    private EventSAGAStatus status;
    
}