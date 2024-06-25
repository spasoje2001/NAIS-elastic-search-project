package com.veljko121.backend.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.veljko121.backend.dto.EmployeeResponseDTO;
import com.veljko121.backend.dto.UserResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.veljko121.backend.core.enums.Role;

import com.veljko121.backend.core.service.impl.CRUDService;
import com.veljko121.backend.model.User;
import com.veljko121.backend.repository.UserRepository;
import com.veljko121.backend.service.IUserService;

@Service
public class UserService extends CRUDService<User, Integer> implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository repository) {
        super(repository);
        this.userRepository = repository;
    }

    @Override
    public User findByUsername(String username) throws NoSuchElementException {
        return userRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return findByUsername(username);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Boolean canUsernameBeChanged(User updatedUser) {
        var oldUser = findById(updatedUser.getId());

        if (existsByUsername(updatedUser.getUsername())) {
            if (oldUser.getUsername().equals(updatedUser.getUsername())) return true; // username did not change
            return false; // username changed, but exists already
        }

        return true; // username changed and doesn't exist
    }

    @Override
    public Boolean canEmailBeChanged(User updatedUser) {
        var oldUser = findById(updatedUser.getId());

        if (existsByEmail(updatedUser.getEmail())) {
            if (oldUser.getEmail().equals(updatedUser.getEmail())) return true; // email didn't change
            return false; // email changed, but exists already
        }

        return true; // email changed and doesn't exist
    }

    @Override
    public Boolean canUsernameBeChanged(User user, String newUsername) {
        if (existsByUsername(newUsername)) {
            if (user.getUsername().equals(newUsername)) return true;
            return false;
        }

        return true;
    }

    @Override
    public Boolean canEmailBeChanged(User user, String newEmail) {
        if (existsByEmail(newEmail)) {
            if (user.getEmail().equals(newEmail)) return true;
            return false;
        }

        return true;
    }

    public List<EmployeeResponseDTO> getAllEmployees() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() != Role.GUEST && user.getRole() != Role.ADMIN)
                .map(this::convertToEmployeeResponseDTO)
                .collect(Collectors.toList());
    }

    private EmployeeResponseDTO convertToEmployeeResponseDTO(User user) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getFirstName() + " " + user.getLastName());
        dto.setRole(user.getRole());
        dto.setEmail(user.getEmail());
        dto.setIsAccountLocked(user.getIsAccountLocked());
        // Add more fields as needed, for example, dto.setIsBlocked(user.getIsBlocked());
        return dto;
    }

    public void switchAccountLockedStatus(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));
        user.setIsAccountLocked(!user.getIsAccountLocked()); // Assuming there's a setter for the isAccountLocked field
        userRepository.save(user);
    }

    public UserResponseDTO getById(Integer userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + userId));
        return convertToUserResponseDTO(user);
    };

    private UserResponseDTO convertToUserResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        dto.setEmail(user.getEmail());
        // Add more fields as needed, for example, dto.setIsBlocked(user.getIsBlocked());
        return dto;
    }
    
}
