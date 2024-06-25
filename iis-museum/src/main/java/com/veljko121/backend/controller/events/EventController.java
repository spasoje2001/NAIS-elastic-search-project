package com.veljko121.backend.controller.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.veljko121.backend.core.enums.EventInvitationStatus;
import com.veljko121.backend.core.service.IJwtService;
import com.veljko121.backend.dto.events.EventInvitationDeclinationRequestDTO;
import com.veljko121.backend.dto.events.EventInvitationResponseDTO;
import com.veljko121.backend.dto.events.EventRequestDTO;
import com.veljko121.backend.dto.events.EventResponseDTO;
import com.veljko121.backend.dto.events.EventUpdateRequestDTO;
import com.veljko121.backend.model.Curator;
import com.veljko121.backend.model.Organizer;
import com.veljko121.backend.model.events.Event;
import com.veljko121.backend.model.events.EventPicture;
import com.veljko121.backend.service.ICuratorService;
import com.veljko121.backend.service.IOrganizerService;
import com.veljko121.backend.service.events.IEventInvitationService;
import com.veljko121.backend.service.events.IEventService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    
    private final IEventService eventService;
    private final IEventInvitationService eventInvitationService;
    private final IOrganizerService organizerService;
    private final ICuratorService curatorService;
    private final IJwtService jwtService;

    private final ModelMapper modelMapper;

    @GetMapping(path = "{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        var event = eventService.findById(id);
        var eventResponse = modelMapper.map(event, EventResponseDTO.class);
        return ResponseEntity.ok().body(eventResponse);
    }
    
    @GetMapping
    public ResponseEntity<?> getAll() {
        var events = eventService.findAll();
        var response = events.stream().map(tour -> modelMapper.map(tour, EventResponseDTO.class)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<?> create(@RequestBody EventRequestDTO requestDTO) {
        var event = mapRequestToEvent(requestDTO);
        eventService.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @PutMapping
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<?> update(@RequestBody EventUpdateRequestDTO requestDTO) {
        var event = mapUpdateRequestToEvent(requestDTO);
        eventService.update(event);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @PatchMapping(path = "{id}/publish")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<?> publish(@PathVariable Integer id) {
        if (!loggedInOrganizerCreatedEvent(id)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        eventService.publish(id);
        return ResponseEntity.ok().build();
    }
    
    @PatchMapping(path = "{id}/archive")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<?> archive(@PathVariable Integer id) {
        if (!loggedInOrganizerCreatedEvent(id)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        eventService.archive(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping(path = "published")
    public ResponseEntity<?> getAllPublished() {
        var events = eventService.findPublished();
        var response = events.stream().map(tour -> modelMapper.map(tour, EventResponseDTO.class)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @GetMapping(path = "my")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<?> getEventsByLoggedInOrganizer() {
        var events = eventService.findByOrganizer(getLoggedInOrganizer());
        var response = events.stream().map(tour -> modelMapper.map(tour, EventResponseDTO.class)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<?> deleteEvent(@PathVariable Integer id) {
        if (!loggedInOrganizerCreatedEvent(id)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        eventService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping(path = "invitations/invite/{eventId}/{curatorId}")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<?> inviteCurator(@PathVariable Integer eventId, @PathVariable Integer curatorId) {
        if (!loggedInOrganizerCreatedEvent(eventId)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        this.eventInvitationService.inviteCurator(eventId, curatorId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @PatchMapping("invitations/accept/{id}")
    @PreAuthorize("hasRole('CURATOR')")
    public ResponseEntity<?> acceptInvitation(@PathVariable Integer id) {
        try {
            if (!loggedInCuratorReceivedInvitation(id)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            this.eventInvitationService.acceptInvitation(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PatchMapping("invitations/decline/{id}")
    @PreAuthorize("hasRole('CURATOR')")
    public ResponseEntity<?> declineInvitation(@PathVariable Integer id, @RequestBody EventInvitationDeclinationRequestDTO requestDTO) {
        try {
            if (!loggedInCuratorReceivedInvitation(id)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            this.eventInvitationService.declineInvitation(id, requestDTO.getDeclinationExplanation());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("invitations/cancel/{id}")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<?> cancelInvitation(@PathVariable Integer id) {
        try {
            if (!loggedInOrganizerCreatedInvitation(id)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            this.eventInvitationService.cancelInvitation(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("invitations/pending")
    @PreAuthorize("hasAnyRole('CURATOR', 'ORGANIZER')")
    public ResponseEntity<?> getPendingInvitations() {
        try {
            var curator = getLoggedInCurator();
            var eventInvitations = eventInvitationService.findByCuratorAndStatus(curator, EventInvitationStatus.PENDING);
            var response = eventInvitations.stream().map(eventInvitation -> modelMapper.map(eventInvitation, EventInvitationResponseDTO.class)).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            var organizer = getLoggedInOrganizer();
            var eventInvitations = eventInvitationService.findByOrganizerAndStatus(organizer, EventInvitationStatus.PENDING);
            var response = eventInvitations.stream().map(eventInvitation -> modelMapper.map(eventInvitation, EventInvitationResponseDTO.class)).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
    
    @GetMapping("invitations/responded")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<?> getRespondedInvitations() {
        var organizer = getLoggedInOrganizer();
        var eventInvitations = eventInvitationService.findByOrganizerAndStatus(organizer, EventInvitationStatus.ACCEPTED);
        var declinedEventInvitations = eventInvitationService.findByOrganizerAndStatus(organizer, EventInvitationStatus.DECLINED);
        eventInvitations.addAll(declinedEventInvitations);
        var response = eventInvitations.stream().map(eventInvitation -> modelMapper.map(eventInvitation, EventInvitationResponseDTO.class)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private Organizer getLoggedInOrganizer() {
        return organizerService.findById(jwtService.getLoggedInUserId());
    }
    
    private Curator getLoggedInCurator() {
        return curatorService.findById(jwtService.getLoggedInUserId());
    }

    private Event mapRequestToEvent(EventRequestDTO requestDTO) {
        var event = modelMapper.map(requestDTO, Event.class);
        event.setOrganizer(getLoggedInOrganizer());

        Collection<EventPicture> eventPictures = new ArrayList<>();
        for (var picturePath : requestDTO.getPicturePaths()) {
            var eventPicture = new EventPicture();
            eventPicture.setPath(picturePath);
            eventPictures.add(eventPicture);
        }
        event.setPictures(eventPictures);
        return event;
    }

    private Event mapUpdateRequestToEvent(EventUpdateRequestDTO updateRequestDTO) {
        var event = mapRequestToEvent(new EventRequestDTO(updateRequestDTO));
        event.setId(updateRequestDTO.getId());
        event.setOrganizer(getLoggedInOrganizer());
        Collection<EventPicture> eventPictures = new ArrayList<>();
        for (var picturePath : updateRequestDTO.getPicturePaths()) {
            var eventPicture = new EventPicture();
            eventPicture.setPath(picturePath);
            eventPictures.add(eventPicture);
        }
        event.setPictures(eventPictures);
        return event;
    }

    private Boolean loggedInOrganizerCreatedEvent(Integer id) {
        var event = eventService.findById(id);
        if (!event.getOrganizer().equals(getLoggedInOrganizer())) return false;
        return true;
    }

    private Boolean loggedInCuratorReceivedInvitation(Integer id) {
        var eventInvitation = eventInvitationService.findById(id);
        if (!eventInvitation.getCurator().equals(getLoggedInCurator())) return false;
        return true;
    }

    private Boolean loggedInOrganizerCreatedInvitation(Integer eventInvitationId) {
        var eventInvitation = eventInvitationService.findById(eventInvitationId);
        return loggedInOrganizerCreatedEvent(eventInvitation.getEvent().getId());
    }

}
