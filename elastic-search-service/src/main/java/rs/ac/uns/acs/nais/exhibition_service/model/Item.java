package rs.ac.uns.acs.nais.exhibition_service.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import rs.ac.uns.acs.nais.exhibition_service.enums.ItemCategory;

import java.io.Serializable;

@Getter
@Setter
public class Item {
    private String name;
    private String description;
    private String authorsName;
    private String yearOfCreation;
    private String period;
    private ItemCategory category;
}
