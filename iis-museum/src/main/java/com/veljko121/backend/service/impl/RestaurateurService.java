package com.veljko121.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.veljko121.backend.core.exception.EmailNotUniqueException;
import com.veljko121.backend.core.exception.UsernameNotUniqueException;
import com.veljko121.backend.core.service.impl.CRUDService;
import com.veljko121.backend.model.Restaurateur;
import com.veljko121.backend.repository.RestaurateurRepository;
import com.veljko121.backend.service.IRestaurateurService;
import com.veljko121.backend.service.IUserService;

@Service
public class RestaurateurService extends CRUDService<Restaurateur, Integer> implements IRestaurateurService{
    
    private final IUserService userService;

    private final RestaurateurRepository restaurateurRepository;

    @Autowired
    public RestaurateurService(RestaurateurRepository repository, RestaurateurRepository restaurateurRepository, IUserService userService) {
        super(repository);
        this.restaurateurRepository = restaurateurRepository;
        this.userService = userService;
    }

    @Override
    public Restaurateur save(Restaurateur restaurateur) {
        if (existsByUsername(restaurateur.getUsername())) throw new UsernameNotUniqueException(restaurateur.getUsername());
        if (existsByEmail(restaurateur.getEmail())) throw new EmailNotUniqueException(restaurateur.getEmail());

        return restaurateurRepository.save(restaurateur);
    }

    private Boolean existsByUsername(String username) {
        return userService.existsByUsername(username);
    }
    
    private Boolean existsByEmail(String email) {
        return userService.existsByEmail(email);
    }

    @Override
    public Restaurateur update(Restaurateur updated) {
        if (!userService.canUsernameBeChanged(updated)) throw new UsernameNotUniqueException(updated.getUsername());
        if (!userService.canEmailBeChanged(updated)) throw new EmailNotUniqueException(updated.getEmail());

        var oldGuest = findById(updated.getId());
        updated.setPassword(oldGuest.getPassword());
        updated.setRole(oldGuest.getRole());

        return restaurateurRepository.save(updated);
    }

    @Override
    public Restaurateur findByUsername(String username) {
        return restaurateurRepository.findByUsername(username).orElseThrow();
    }

}
