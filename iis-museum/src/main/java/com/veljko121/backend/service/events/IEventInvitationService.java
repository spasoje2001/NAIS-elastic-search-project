package com.veljko121.backend.service.events;

import java.util.Collection;

import com.veljko121.backend.core.enums.EventInvitationStatus;
import com.veljko121.backend.core.service.ICRUDService;
import com.veljko121.backend.model.Curator;
import com.veljko121.backend.model.Organizer;
import com.veljko121.backend.model.events.EventInvitation;

public interface IEventInvitationService extends ICRUDService<EventInvitation, Integer> {

    void inviteCurators(Integer eventId, Collection<Integer> curatorIds);

    void inviteCurator(Integer eventId, Integer curatorId);
    
    void acceptInvitation(Integer id);
    
    void declineInvitation(Integer id, String explanation);
    
    void cancelInvitation(Integer id);

    Collection<EventInvitation> findByCuratorAndStatus(Curator curator, EventInvitationStatus status);

    Collection<EventInvitation> findByOrganizerAndStatus(Organizer organizer, EventInvitationStatus status);

}
