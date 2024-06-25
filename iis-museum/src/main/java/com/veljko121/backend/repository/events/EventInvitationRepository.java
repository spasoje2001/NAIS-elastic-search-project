package com.veljko121.backend.repository.events;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.veljko121.backend.core.enums.EventInvitationStatus;
import com.veljko121.backend.model.Curator;
import com.veljko121.backend.model.Organizer;
import com.veljko121.backend.model.events.EventInvitation;

public interface EventInvitationRepository extends JpaRepository<EventInvitation, Integer> {

    List<EventInvitation> findByCuratorAndStatus(Curator curator, EventInvitationStatus status);

    @Query("SELECT ei FROM EventInvitation ei WHERE ei.event.organizer = :organizer AND ei.status = :status")
    List<EventInvitation> findByOrganizerAndStatus(@Param("organizer") Organizer organizer, @Param("status") EventInvitationStatus status);

}
