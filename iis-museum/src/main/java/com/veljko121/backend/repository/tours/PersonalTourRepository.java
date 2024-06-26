package com.veljko121.backend.repository.tours;

import org.springframework.data.jpa.repository.JpaRepository;
import com.veljko121.backend.model.tours.PersonalTour;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonalTourRepository extends JpaRepository<PersonalTour, Integer> {

    @Query("SELECT t FROM PersonalTour t LEFT JOIN FETCH t.exhibitions WHERE t.proposer.id = ?1")
    List<PersonalTour> findByGuestId(Integer guestId);

}
