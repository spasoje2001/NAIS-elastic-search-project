package rs.ac.uns.acs.nais.api_gateway.model;

import java.time.LocalDateTime;
import java.util.Collection;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class Event {
 
    @NotEmpty
    private String name;

    private String description;

    @NotNull
    private LocalDateTime startDateTime;
    
    @Positive
    @NotNull
    private Integer durationMinutes;

    @Positive
    private Integer ticketsNumber;
    
    @PositiveOrZero
    private Integer price;

    private Integer roomId;

    private Collection<String> picturePaths;

}
