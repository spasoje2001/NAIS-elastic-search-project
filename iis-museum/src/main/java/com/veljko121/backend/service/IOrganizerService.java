package com.veljko121.backend.service;

import com.veljko121.backend.core.service.ICRUDService;
import com.veljko121.backend.model.Organizer;

public interface IOrganizerService  extends ICRUDService<Organizer, Integer>{

    Organizer update(Organizer updated);

    Organizer findByUsername(String username);

}
