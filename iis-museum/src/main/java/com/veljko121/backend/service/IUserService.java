package com.veljko121.backend.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.veljko121.backend.dto.EmployeeResponseDTO;
import com.veljko121.backend.dto.UserResponseDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.veljko121.backend.core.service.ICRUDService;
import com.veljko121.backend.model.User;

public interface IUserService extends UserDetailsService, ICRUDService<User, Integer> {

    User findByUsername(String username) throws NoSuchElementException;

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean canUsernameBeChanged(User updatedUser);
    
    Boolean canEmailBeChanged(User updatedUser);

    Boolean canUsernameBeChanged(User user, String newUsername);

    Boolean canEmailBeChanged(User user, String newEmail);

    List<EmployeeResponseDTO> getAllEmployees();

    UserResponseDTO getById(Integer userId);

    void switchAccountLockedStatus(Integer userId);


}
