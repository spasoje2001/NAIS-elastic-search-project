package com.veljko121.backend.dto;

import com.veljko121.backend.core.enums.Role;

import lombok.Data;

@Data
public class UserResponseDTO {
    
    private Integer id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;

}
