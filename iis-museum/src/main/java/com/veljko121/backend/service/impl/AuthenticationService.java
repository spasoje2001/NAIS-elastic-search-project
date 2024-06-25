package com.veljko121.backend.service.impl;

import com.veljko121.backend.core.enums.Role;
import com.veljko121.backend.dto.UpdateEmployeeRequestDTO;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.veljko121.backend.dto.CredentialsDTO;
import com.veljko121.backend.model.Curator;
import com.veljko121.backend.model.Guest;
import com.veljko121.backend.model.Organizer;
import com.veljko121.backend.model.Restaurateur;
import com.veljko121.backend.model.User;
import com.veljko121.backend.service.IAuthenticationService;
import com.veljko121.backend.service.ICuratorService;
import com.veljko121.backend.service.IGuestService;
import com.veljko121.backend.service.IOrganizerService;
import com.veljko121.backend.service.IRestaurateurService;
import com.veljko121.backend.service.IUserService;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final IUserService userService;

    private final IGuestService guestService;

    private final ICuratorService curatorService;

    private final IRestaurateurService restaurateurService;

    private final IOrganizerService organizerService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(Guest guest) {
        //guest.setRole(Role.GUEST);
        guest.setPassword(passwordEncoder.encode(guest.getPassword()));
        return guestService.save(guest);
    }

    @Override
    public User registerCurator(Curator curator) {
        //curator.setRole(Role.CURATOR);
        curator.setPassword(passwordEncoder.encode(curator.getPassword()));
        return curatorService.save(curator);
    }

    @Override
    public User registerOrganizer(Organizer organizer) {
        //organizer.setRole(Role.ORGANIZER);
        organizer.setPassword(passwordEncoder.encode(organizer.getPassword()));
        return organizerService.save(organizer);
    }

    @Override
    public User registerRestaurateur(Restaurateur restaurateur) {
        //restaurateur.setRole(Role.RESTAURATEUR);
        restaurateur.setPassword(passwordEncoder.encode(restaurateur.getPassword()));
        return restaurateurService.save(restaurateur);
    }

    @Transactional
    public void updateEmployee(Integer id, UpdateEmployeeRequestDTO requestDTO) {
        User user = userService.findById(id);
        Role currentRole = user.getRole();
        String biography = "";
        boolean roleChanged = !currentRole.equals(requestDTO.getRole());

        user.setRole(requestDTO.getRole());
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setEmail(requestDTO.getEmail());
        user.setUsername(requestDTO.getUsername());

        if (StringUtils.hasText(requestDTO.getPassword())) {
            user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        }

        userService.save(user);
        // Check if the role has changed
        if (roleChanged) {
            // Handle role change
            switch (currentRole) {
                case CURATOR:
                    // Remove from curator table
                    Curator curator = curatorService.findById(id);
                    biography = curator.getBiography();
                    curatorService.deleteById(id);
                    break;
                case ORGANIZER:
                    // Remove from organizer table
                    Organizer organizer = organizerService.findById(id);
                    biography = organizer.getBiography();
                    organizerService.deleteById(id);
                    break;
                // ... other cases
                case RESTAURATEUR:
                    Restaurateur restaurateur = restaurateurService.findById(id);
                    biography = restaurateur.getBiography();
                    restaurateurService.deleteById(id);
                    break;
            }

            switch (requestDTO.getRole()) {
                case CURATOR:
                    // Add to curator table
                    Curator curator = new Curator();
                    copyUserFieldsToSubclass(user, curator);
                    curator.setBiography(biography);
                    curatorService.save(curator);
                    break;
                case ORGANIZER:
                    // Add to organizer table
                    Organizer organizer = new Organizer();
                    copyUserFieldsToSubclass(user, organizer);
                    organizer.setBiography(biography);
                    organizerService.save(organizer);
                    break;
                case RESTAURATEUR:
                    Restaurateur restaurateur = new Restaurateur();
                    copyUserFieldsToSubclass(user, restaurateur);
                    restaurateur.setBiography(biography);
                    restaurateurService.save(restaurateur);
                    break;
                // ... other cases
            }
        }
    }

    private void copyUserFieldsToSubclass(User user, User subclass) {
        // Copy common fields
        subclass.setFirstName(user.getFirstName());
        subclass.setLastName(user.getLastName());
        subclass.setEmail(user.getEmail());
        subclass.setUsername(user.getUsername());
        subclass.setRole(user.getRole());
        subclass.setPassword(user.getPassword());
        // ... other common fields
    }

    
    @Override
    public User login(CredentialsDTO credentialsDTO) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(credentialsDTO.getUsername(), credentialsDTO.getPassword())
        );
        return userService.findByUsername(credentialsDTO.getUsername());
    }

    @Override
    public Boolean usernameExists(String username) {
        return userService.existsByUsername(username);
    }

    @Override
    public Boolean emailExists(String email) {
        return userService.existsByEmail(email);
    }
    
}
