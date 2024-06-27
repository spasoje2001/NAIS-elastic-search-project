package com.veljko121.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CredentialsDTO {

    @NotEmpty
    private String username;
    
    @NotEmpty
    private String password;
    
}
