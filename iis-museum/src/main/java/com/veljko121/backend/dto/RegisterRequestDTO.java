package com.veljko121.backend.dto;

import com.veljko121.backend.core.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    
    @NotEmpty
    private String username;

    @Email
    @NotEmpty
    private String email;
    
    @NotEmpty
    private String password;

    private String firstName;

    private String lastName;

    private Role role;
    
}
