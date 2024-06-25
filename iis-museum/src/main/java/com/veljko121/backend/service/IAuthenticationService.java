package com.veljko121.backend.service;

import com.veljko121.backend.dto.CredentialsDTO;
import com.veljko121.backend.dto.UpdateEmployeeRequestDTO;
import com.veljko121.backend.model.Curator;
import com.veljko121.backend.model.Guest;
import com.veljko121.backend.model.Organizer;
import com.veljko121.backend.model.Restaurateur;
import com.veljko121.backend.model.User;

public interface IAuthenticationService {
    
    User register(Guest guest);

    User login(CredentialsDTO credentialsDTO);

    Boolean usernameExists(String username);

    Boolean emailExists(String email);

    User registerCurator(Curator curator);

    User registerOrganizer(Organizer organizer);

    User registerRestaurateur(Restaurateur restaurateur);

    void updateEmployee(Integer id, UpdateEmployeeRequestDTO requestDTO);

}
