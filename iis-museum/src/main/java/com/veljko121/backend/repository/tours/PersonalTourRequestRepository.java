package com.veljko121.backend.repository.tours;

import com.veljko121.backend.model.tours.PersonalTourRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonalTourRequestRepository extends JpaRepository<PersonalTourRequest, Integer> {

    @Query("SELECT r FROM PersonalTourRequest r WHERE r.proposer.id = ?1")
    List<PersonalTourRequest> findByGuestId(Integer guestId);

    @Query("SELECT r FROM PersonalTourRequest r WHERE r.status = 'IN_PROGRESS'")
    List<PersonalTourRequest> findInProgress();

    @Query("SELECT r FROM PersonalTourRequest r WHERE r.organizer.id = ?1")
    List<PersonalTourRequest> findByOrganizerId(Integer organizerId);

}
