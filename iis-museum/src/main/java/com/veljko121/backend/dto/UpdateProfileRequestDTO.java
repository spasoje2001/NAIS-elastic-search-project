package com.veljko121.backend.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequestDTO {

    private String username;
    
    @Email
    private String email;
    
    private String firstName;
    
    private String lastName;
    
}
