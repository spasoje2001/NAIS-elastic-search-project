package com.veljko121.backend.dto;

import lombok.Data;

@Data
public class OrchestratorRequestDTO {

    private Integer id;
    public String name;
    public String shortDescription;
    public String longDescription;
    
}
