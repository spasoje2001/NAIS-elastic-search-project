package com.veljko121.backend.service.impl.events;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.veljko121.backend.core.enums.EventInvitationStatus;
import com.veljko121.backend.core.service.impl.CRUDService;
import com.veljko121.backend.model.Curator;
import com.veljko121.backend.model.Organizer;
import com.veljko121.backend.model.events.EventInvitation;
import com.veljko121.backend.repository.events.EventInvitationRepository;
import com.veljko121.backend.service.ICuratorService;
import com.veljko121.backend.service.events.IEventInvitationService;
import com.veljko121.backend.service.events.IEventService;

@Service
public class EventInvitationService extends CRUDService<EventInvitation, Integer> implements IEventInvitationService {

    private final EventInvitationRepository eventInvitationRepository;
    private final ICuratorService curatorService;
    private final IEventService eventService;
    
    public EventInvitationService(EventInvitationRepository repository, ICuratorService curatorService, IEventService eventService) {
        super(repository);
        this.eventInvitationRepository = repository;
        this.curatorService = curatorService;
        this.eventService = eventService;
    }

    @Override
    public void inviteCurators(Integer eventId, Collection<Integer> curatorIds) {
        var event = eventService.findById(eventId);
        var eventInvitations = new ArrayList<EventInvitation>();
        for (var curatorId : curatorIds) {
            var curator = curatorService.findById(curatorId);
            var eventInvitation = new EventInvitation();
            eventInvitation.setCurator(curator);
            eventInvitation.setEvent(event);
            eventInvitation.setStatus(EventInvitationStatus.PENDING);
            eventInvitations.add(eventInvitation);
        }
        this.eventInvitationRepository.saveAll(eventInvitations);
    }

    @Override
    public void acceptInvitation(Integer id) {
        var eventInvitation = findById(id);
        if (!eventInvitation.getStatus().equals(EventInvitationStatus.PENDING)) throw new RuntimeException("Invitation has already been responded on.");
        eventInvitation.setStatus(EventInvitationStatus.ACCEPTED);
        eventInvitationRepository.save(eventInvitation);
    }

    @Override
    public void declineInvitation(Integer id, String explanation) {
        var eventInvitation = findById(id);
        if (!eventInvitation.getStatus().equals(EventInvitationStatus.PENDING)) throw new RuntimeException("Invitation has already been responded on.");
        eventInvitation.setStatus(EventInvitationStatus.DECLINED);
        eventInvitation.setDeclinationExplanation(explanation);
        eventInvitationRepository.save(eventInvitation);
    }

    @Override
    public void cancelInvitation(Integer id) {
        var eventInvitation = findById(id);
        if (!eventInvitation.getStatus().equals(EventInvitationStatus.PENDING)) throw new RuntimeException("Invitation has already been responded on.");
        eventInvitation.setStatus(EventInvitationStatus.CANCELED);
        eventInvitationRepository.save(eventInvitation);
    }

    @Override
    public Collection<EventInvitation> findByCuratorAndStatus(Curator curator, EventInvitationStatus status) {
        return eventInvitationRepository.findByCuratorAndStatus(curator, status);
    }

    @Override
    public Collection<EventInvitation> findByOrganizerAndStatus(Organizer organizer, EventInvitationStatus status) {
        return eventInvitationRepository.findByOrganizerAndStatus(organizer, status);
    }

    @Override
    public void inviteCurator(Integer eventId, Integer curatorId) {
        var event = eventService.findById(eventId);
        var curator = curatorService.findById(curatorId);
        var eventInvitation = new EventInvitation();
        eventInvitation.setCurator(curator);
        eventInvitation.setEvent(event);
        eventInvitation.setStatus(EventInvitationStatus.PENDING);
        this.eventInvitationRepository.save(eventInvitation);
    }

}
