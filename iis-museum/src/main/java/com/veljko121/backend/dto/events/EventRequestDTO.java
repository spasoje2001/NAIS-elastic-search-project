package com.veljko121.backend.dto.events;

import java.time.LocalDateTime;
import java.util.Collection;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventRequestDTO {
 
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

    public EventRequestDTO(EventUpdateRequestDTO eventUpdateRequestDTO) {
        this.name = eventUpdateRequestDTO.getName();
        this.description = eventUpdateRequestDTO.getDescription();
        this.startDateTime = eventUpdateRequestDTO.getStartDateTime();
        this.durationMinutes = eventUpdateRequestDTO.getDurationMinutes();
        this.ticketsNumber = eventUpdateRequestDTO.getTicketsNumber();
        this.price = eventUpdateRequestDTO.getPrice();
        this.roomId = eventUpdateRequestDTO.getRoomId();
        this.picturePaths = eventUpdateRequestDTO.getPicturePaths();
    }
    
}
