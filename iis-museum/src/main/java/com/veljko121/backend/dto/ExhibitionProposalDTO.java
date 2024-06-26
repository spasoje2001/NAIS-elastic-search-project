package com.veljko121.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ExhibitionProposalDTO {
    @NotBlank(message = "Start date is required.")
    private String startDate;
    @NotBlank(message = "End date is required.")
    private String endDate;
    @PositiveOrZero(message = "Price must be positive or zero.")
    private Integer price;
    @NotNull(message = "Room ID is required.")
    private Integer roomId;
    @NotNull(message = "Organizer ID is required.")
    private Integer organizerId;
    @NotNull(message = "Curator ID is required.")
    private Integer curatorId;
    
    private String name;
    private String shortDescription;
    private String longDescription;
}
