package com.veljko121.backend.dto;

import com.veljko121.backend.core.enums.Role;

import lombok.Data;

@Data
public class EmployeeResponseDTO {
    private Integer id;
    private String name;
    private Role role;
    private String email;
    private Boolean isAccountLocked;
}
