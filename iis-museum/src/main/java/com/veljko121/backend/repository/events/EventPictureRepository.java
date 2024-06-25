package com.veljko121.backend.repository.events;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veljko121.backend.model.events.EventPicture;

public interface EventPictureRepository extends JpaRepository<EventPicture, Integer> { }
