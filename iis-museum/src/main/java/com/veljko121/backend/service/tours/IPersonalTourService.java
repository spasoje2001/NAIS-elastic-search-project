package com.veljko121.backend.service.tours;

import com.veljko121.backend.core.service.ICRUDService;
import com.veljko121.backend.model.tours.PersonalTour;

import java.util.List;

public interface IPersonalTourService extends ICRUDService<PersonalTour, Integer> {

    List<PersonalTour> findByGuestId(Integer guestId);

}
