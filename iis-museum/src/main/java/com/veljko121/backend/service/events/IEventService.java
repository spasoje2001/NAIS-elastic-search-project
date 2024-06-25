package com.veljko121.backend.service.events;

import java.util.Collection;

import com.veljko121.backend.core.service.ICRUDService;
import com.veljko121.backend.model.Organizer;
import com.veljko121.backend.model.events.Event;

public interface IEventService extends ICRUDService<Event, Integer> {

    void publish(Integer id);

    void archive(Integer id);
    
    Collection<Event> findPublished();

    Collection<Event> findByOrganizer(Organizer organizer);

    Event update(Event entity);
    
}
