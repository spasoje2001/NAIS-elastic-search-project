package com.veljko121.backend.repository.events;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veljko121.backend.core.enums.EventStatus;
import com.veljko121.backend.model.Organizer;
import com.veljko121.backend.model.events.MuseumEvent;

public interface EventRepository extends JpaRepository<MuseumEvent, Integer> { 

    List<MuseumEvent> findByStatus(EventStatus status);

    List<MuseumEvent> findByOrganizer(Organizer organizer);

 }
