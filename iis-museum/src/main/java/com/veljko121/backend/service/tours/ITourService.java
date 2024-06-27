package com.veljko121.backend.service.tours;

import com.veljko121.backend.core.service.ICRUDService;
import com.veljko121.backend.model.tours.Tour;

import java.util.List;

public interface ITourService extends ICRUDService<Tour, Integer> {

    List<Tour> findAll();

    List<Tour> findByOrganizerId(Integer organizerId);

    Tour update(Tour updated);

    void delete(Tour updated);
}
