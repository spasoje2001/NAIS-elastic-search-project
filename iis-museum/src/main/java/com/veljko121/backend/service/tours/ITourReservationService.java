package com.veljko121.backend.service.tours;

import com.veljko121.backend.core.service.ICRUDService;
import com.veljko121.backend.model.tours.Tour;
import com.veljko121.backend.model.tours.TourReservation;

import java.util.List;

public interface ITourReservationService extends ICRUDService<TourReservation, Integer> {

    List<TourReservation> findByGuestId(Integer guestId);

    TourReservation update(TourReservation updated);

    void delete(TourReservation updated);

}
