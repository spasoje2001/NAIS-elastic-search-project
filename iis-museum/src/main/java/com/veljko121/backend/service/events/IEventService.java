package com.veljko121.backend.service.events;

import java.util.Collection;

import com.veljko121.backend.core.service.ICRUDService;
import com.veljko121.backend.model.Organizer;
import com.veljko121.backend.model.events.MuseumEvent;

public interface IEventService extends ICRUDService<MuseumEvent, Integer> {

    void publish(Integer id);

    void archive(Integer id);
    
    Collection<MuseumEvent> findPublished();

    Collection<MuseumEvent> findByOrganizer(Organizer organizer);

    MuseumEvent update(MuseumEvent entity);
    
}
