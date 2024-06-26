package com.veljko121.backend.service.impl.tours;

import com.veljko121.backend.core.service.impl.CRUDService;
import com.veljko121.backend.model.tours.TourReservation;
import com.veljko121.backend.repository.tours.TourReservationRepository;
import com.veljko121.backend.service.tours.ITourReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TourReservationService extends CRUDService<TourReservation, Integer> implements ITourReservationService {

    @Autowired
    private final TourReservationRepository reservationRepository;

    public TourReservationService(JpaRepository<TourReservation, Integer> repository, TourReservationRepository reservationRepository) {
        super(repository);
        this.reservationRepository = reservationRepository;
    }

    @Override
    public TourReservation save(TourReservation tourReservation) {
        return reservationRepository.save(tourReservation);
    }

    public List<TourReservation> findByGuestId(Integer guestId){
        return reservationRepository.findByGuestId(guestId);
    }

    @Override
    public TourReservation update(TourReservation updated) {
        return reservationRepository.save(updated);
    }

    @Override
    public void delete(TourReservation reservation){
        reservationRepository.delete(reservation);
    }
}
