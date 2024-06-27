package com.veljko121.backend.service;

import com.veljko121.backend.core.service.ICRUDService;
import com.veljko121.backend.model.Restaurateur;

public interface IRestaurateurService extends ICRUDService<Restaurateur, Integer> {

    Restaurateur update(Restaurateur updated);

    Restaurateur findByUsername(String username);

}
