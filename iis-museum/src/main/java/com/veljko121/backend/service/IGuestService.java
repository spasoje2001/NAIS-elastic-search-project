package com.veljko121.backend.service;

import com.veljko121.backend.core.service.ICRUDService;
import com.veljko121.backend.model.Guest;

public interface IGuestService extends ICRUDService<Guest, Integer> {

    Guest update(Guest updated);

    Guest findByUsername(String username);

}
