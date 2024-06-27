package com.veljko121.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuratorUpdateProfileRequestDTO extends UpdateProfileRequestDTO {

    private String biography;
    
}
