package com.veljko121.backend.core.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BooleanResponseDTO {

    @NotEmpty
    private Boolean value;

}
