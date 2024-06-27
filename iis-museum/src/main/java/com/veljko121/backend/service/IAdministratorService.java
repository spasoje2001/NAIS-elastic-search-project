package com.veljko121.backend.service;

import com.veljko121.backend.core.service.ICRUDService;
import com.veljko121.backend.model.Administrator;

public interface IAdministratorService extends ICRUDService<Administrator, Integer> {

    Administrator update(Administrator updated);

    Administrator findByUsername(String username);
        
}