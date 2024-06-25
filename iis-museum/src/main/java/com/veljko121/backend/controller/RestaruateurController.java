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
import com.veljko121.backend.dto.RestaurateurResponseDTO;
import com.veljko121.backend.dto.RestaurateurUpdateProfileRequestDTO;
import com.veljko121.backend.model.Restaurateur;
import com.veljko121.backend.service.IRestaurateurService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users/restaurateurs")
@PreAuthorize("hasRole('RESTAURATEUR')")
@RequiredArgsConstructor
public class RestaruateurController {
    
    private final IRestaurateurService restaurateurService;
    private final IJwtService jwtService;

    private final ModelMapper modelMapper;
    private final Logger logger;

    @GetMapping(path = "{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        var restaurateur = restaurateurService.findById(id);
        var restaurateurResponse = modelMapper.map(restaurateur, RestaurateurResponseDTO.class);
        return ResponseEntity.ok().body(restaurateurResponse);
    }
    
    @GetMapping(path = "profile")
    public ResponseEntity<?> getProfile() {
        return getById(jwtService.getLoggedInUserId());
    }
    
    @PutMapping
    public ResponseEntity<?> updateProfile(@RequestBody RestaurateurUpdateProfileRequestDTO requestDTO) {
        try {
            var updated = modelMapper.map(requestDTO, Restaurateur.class);
            var loggedInUserId = jwtService.getLoggedInUserId();
            var loggedInUser = restaurateurService.findById(loggedInUserId);
            updated.setId(loggedInUser.getId());
            updated = restaurateurService.update(updated);
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
