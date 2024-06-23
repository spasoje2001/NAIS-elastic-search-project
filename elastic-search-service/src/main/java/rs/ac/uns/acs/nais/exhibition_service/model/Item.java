package rs.ac.uns.acs.nais.exhibition_service.model;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import rs.ac.uns.acs.nais.exhibition_service.enums.ItemCategory;

public class Item {
    @Id
    private String id;
    private String name;
    private String description;
    private String authorsName;
    private String yearOfCreation;
    private String period;
    private ItemCategory category;
}
