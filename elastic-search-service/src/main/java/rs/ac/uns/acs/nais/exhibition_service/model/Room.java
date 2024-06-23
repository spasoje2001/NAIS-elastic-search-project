package rs.ac.uns.acs.nais.exhibition_service.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "room")
@Getter
@Setter
public class Room {
    @Id
    private String id;
    private String name;
    private String floor;
    private String number;
}
