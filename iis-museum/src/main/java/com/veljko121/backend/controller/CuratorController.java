package com.veljko121.backend.controller;

import com.veljko121.backend.dto.CuratorResponseDTO;
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

import com.veljko121.backend.core.dto.BooleanResponseDTO;
import com.veljko121.backend.core.dto.ErrorResponseDTO;
import com.veljko121.backend.core.exception.EmailNotUniqueException;
import com.veljko121.backend.core.exception.UsernameNotUniqueException;
import com.veljko121.backend.core.service.IJwtService;
import com.veljko121.backend.dto.AuthenticationResponseDTO;
import com.veljko121.backend.dto.CuratorUpdateProfileRequestDTO;
import com.veljko121.backend.model.Curator;
import com.veljko121.backend.service.ICuratorService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/users/curators")
@PreAuthorize("hasRole('CURATOR')")
@RequiredArgsConstructor
public class CuratorController {
    
    private final ICuratorService curatorService;
    private final IJwtService jwtService;

    private final ModelMapper modelMapper;
    private final Logger logger;

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('ORGANIZER', 'GUEST', 'RESTAURATEUR')")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        var curator = curatorService.findById(id);
        var curatorResponse = modelMapper.map(curator, Curator.class);
        return ResponseEntity.ok().body(curatorResponse);
    }



    @GetMapping(path = "profile")
    public ResponseEntity<?> getProfile() {
        return getById(jwtService.getLoggedInUserId());
    }


    
    @PutMapping
    public ResponseEntity<?> updateProfile(@RequestBody CuratorUpdateProfileRequestDTO requestDTO) {
        try {
            var updated = modelMapper.map(requestDTO, Curator.class);
            var loggedInUserId = jwtService.getLoggedInUserId();
            var loggedInUser = curatorService.findById(loggedInUserId);
            updated.setId(loggedInUser.getId());
            updated = curatorService.update(updated);
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

    @GetMapping("by-username/{username}")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<?> findCuratorByUsername(@PathVariable String username) {
        var curator = curatorService.findByUsername(username);
        var curatorResponse = modelMapper.map(curator, Curator.class);
        return ResponseEntity.ok().body(curatorResponse);
    }

    @GetMapping
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<?> findAll() {
        List<Curator> curators = curatorService.findAll();
        var curatorResponses = curators.stream()
                .map(curator -> modelMapper.map(curator, Curator.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(curatorResponses);
    }

    @GetMapping("exists-by-username/{username}")
    @PreAuthorize("hasAnyRole('ORGANIZER', 'CURATOR', 'ADMINISTRATOR')")
    public ResponseEntity<?> existsByUsername(@PathVariable String username) {
        try {
            this.curatorService.findByUsername(username);
            return ResponseEntity.ok().body(new BooleanResponseDTO(true));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new BooleanResponseDTO(false));
        }
    }
    

}
