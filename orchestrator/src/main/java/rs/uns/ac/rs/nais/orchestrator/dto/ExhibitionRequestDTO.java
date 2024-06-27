package rs.uns.ac.rs.nais.orchestrator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExhibitionRequestDTO {

    private Integer id;
    public String name;
    public String shortDescription;
    public String longDescription;

}
