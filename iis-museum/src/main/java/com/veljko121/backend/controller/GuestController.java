package com.veljko121.backend.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.veljko121.backend.core.dto.ErrorResponseDTO;
import com.veljko121.backend.core.exception.EmailNotUniqueException;
import com.veljko121.backend.core.exception.UsernameNotUniqueException;
import com.veljko121.backend.core.service.IJwtService;
import com.veljko121.backend.dto.AuthenticationResponseDTO;
import com.veljko121.backend.dto.GuestResponseDTO;
import com.veljko121.backend.dto.GuestUpdateProfileRequestDTO;
import com.veljko121.backend.model.Guest;
import com.veljko121.backend.service.IGuestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users/guests")
@PreAuthorize("hasRole('GUEST')")
@RequiredArgsConstructor
public class GuestController {
    
    private final IGuestService guestService;
    private final IJwtService jwtService;

    private final ModelMapper modelMapper;
    private final Logger logger;

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('GUEST', 'ORGANIZER', 'ADMIN')")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        var guest = guestService.findById(id);
        var guestResponse = modelMapper.map(guest, GuestResponseDTO.class);
        return ResponseEntity.ok().body(guestResponse);
    }
    
    @GetMapping(path = "profile")
    public ResponseEntity<?> getProfile() {
        return getById(jwtService.getLoggedInUserId());
    }
    
    @PutMapping
    public ResponseEntity<?> updateProfile(@RequestBody GuestUpdateProfileRequestDTO requestDTO) {
        try {
            var updated = modelMapper.map(requestDTO, Guest.class);
            var loggedInUserId = jwtService.getLoggedInUserId();
            var loggedInUser = guestService.findById(loggedInUserId);
            updated.setId(loggedInUser.getId());
            updated = guestService.update(updated);
            var jwt = jwtService.generateJwt(updated);
            var authenticationResponse = new AuthenticationResponseDTO(jwt);
    
            return ResponseEntity.ok().body(authenticationResponse);

        } catch (UsernameNotUniqueException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
            
        } catch (EmailNotUniqueException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

}
