package com.veljko121.backend.repository.tours;

import com.veljko121.backend.model.tours.TourReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TourReservationRepository extends JpaRepository<TourReservation, Integer> {

    @Query("SELECT t FROM TourReservation t WHERE t.guest.id = :guestId")
    List<TourReservation> findByGuestId(Integer guestId);

}
