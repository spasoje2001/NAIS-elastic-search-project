package com.veljko121.backend.controller;


import com.veljko121.backend.dto.EmployeeResponseDTO;
import com.veljko121.backend.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/employees")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class EmployeeController {
    private final IUserService userService;
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        List<EmployeeResponseDTO> employees = userService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/toggle-lock/{id}")
    public ResponseEntity<?> toggleEmployeeLockStatus(@PathVariable Integer id) {
        userService.switchAccountLockedStatus(id);
        return ResponseEntity.ok().build();
    }
}
