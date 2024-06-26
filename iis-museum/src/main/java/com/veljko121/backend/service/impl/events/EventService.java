package com.veljko121.backend.service.impl.events;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veljko121.backend.core.enums.EventStatus;
import com.veljko121.backend.core.exception.RoomNotAvailableException;
import com.veljko121.backend.core.service.impl.CRUDService;
import com.veljko121.backend.dto.MuseumEventForElasticDTO;
import com.veljko121.backend.dto.OrganizerForElasticDTO;
import com.veljko121.backend.model.Organizer;
import com.veljko121.backend.model.events.MuseumEvent;
import com.veljko121.backend.repository.RoomRepository;
import com.veljko121.backend.repository.events.EventPictureRepository;
import com.veljko121.backend.repository.events.EventRepository;
import com.veljko121.backend.service.IRoomReservationService;
import com.veljko121.backend.service.events.IEventService;

import io.nats.client.Connection;
import jakarta.transaction.Transactional;

@Service
public class EventService extends CRUDService<MuseumEvent, Integer> implements IEventService {

    private final EventRepository eventRepository;
    private final RoomRepository roomRepository;
    private final EventPictureRepository eventPictureRepository;

    private final IRoomReservationService roomReservationService;

    private final Connection natsConnection;

    public EventService(EventRepository repository, RoomRepository roomRepository, IRoomReservationService roomReservationService, EventPictureRepository eventPictureRepository, Connection natsConnection) {
        super(repository);
        this.eventRepository = repository;
        this.roomRepository = roomRepository;
        this.roomReservationService = roomReservationService;
        this.eventPictureRepository = eventPictureRepository;
        this.natsConnection = natsConnection;
    }

    @Override
    @Transactional
    public MuseumEvent save(MuseumEvent entity) throws RoomNotAvailableException {
        var room = roomRepository.findById(entity.getRoomReservation().getRoom().getId()).orElseThrow();
        var roomReservation = entity.getRoomReservation();
        
        LocalDateTime endDateTime = entity.getStartDateTime().plusMinutes(entity.getDurationMinutes());
        roomReservation.setEndDateTime(endDateTime);
        roomReservation.setStartDateTime(entity.getStartDateTime());

        if (!roomReservationService.isRoomAvailable(room, entity.getStartDateTime(), entity.getDurationMinutes())) {
            throw new RoomNotAvailableException(roomReservation);
        }

        roomReservation = roomReservationService.save(roomReservation);
        for (var eventPicture : entity.getPictures()) {
            eventPictureRepository.save(eventPicture);
        }

        entity.setRoomReservation(roomReservation);
        entity.setStatus(EventStatus.DRAFT);

        var savedEntity = super.save(entity);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            var eventForElastic = mapToEventForElastic(entity);
            String json = objectMapper.writeValueAsString(eventForElastic);
            natsConnection.publish("museum-events", json.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return savedEntity;
    }

    @Override
    public void publish(Integer id) {
        var event = findById(id);
        event.setStatus(EventStatus.PUBLISHED);
        super.save(event);
    }

    @Override
    public Collection<MuseumEvent> findPublished() {
        return eventRepository.findByStatus(EventStatus.PUBLISHED);
    }

    @Override
    public Collection<MuseumEvent> findByOrganizer(Organizer organier) {
        return eventRepository.findByOrganizer(organier);
    }

    @Override
    public void archive(Integer id) {
        var event = findById(id);
        event.setStatus(EventStatus.DRAFT);
        super.save(event);
    }

    @Override
    public MuseumEvent update(MuseumEvent entity) {
        var oldEvent = eventRepository.findById(entity.getId()).orElseThrow();
        var room = roomRepository.findById(entity.getRoomReservation().getRoom().getId()).orElseThrow();
        var roomReservation = oldEvent.getRoomReservation();

        var id = oldEvent.getId();
        
        if (!roomReservationService.isRoomAvailableForUpdating(room, entity.getStartDateTime(), entity.getDurationMinutes(), roomReservation)) {
            throw new RoomNotAvailableException(roomReservation);
        }

        roomReservation.setEndDateTime(entity.getStartDateTime().plusMinutes(entity.getDurationMinutes()));
        roomReservation.setStartDateTime(entity.getStartDateTime());
        roomReservation = roomReservationService.save(roomReservation);
        for (var eventPicture : oldEvent.getPictures()) {
            eventPictureRepository.delete(eventPicture);
        }
        for (var eventPicture : entity.getPictures()) {
            eventPictureRepository.save(eventPicture);
        }

        entity.setRoomReservation(roomReservation);
        entity.setStatus(EventStatus.DRAFT);
        entity.setId(id);

        return super.save(entity);
    }
    
    private MuseumEventForElasticDTO mapToEventForElastic(MuseumEvent museumEvent) {

        var eventForElastic = new MuseumEventForElasticDTO();
        
        eventForElastic.setId(museumEvent.getId().toString());
        eventForElastic.setName(museumEvent.getName());
        eventForElastic.setDescription(museumEvent.getDescription());

        var startDateTime = museumEvent.getStartDateTime();
        Date date = new Date(startDateTime.getYear(), startDateTime.getMonthValue(), startDateTime.getDayOfMonth(), startDateTime.getHour(), startDateTime.getMinute(), startDateTime.getSecond());
        eventForElastic.setStartDateTime(date);

        eventForElastic.setDurationMinutes(museumEvent.getDurationMinutes());
        eventForElastic.setPrice(museumEvent.getPrice());
        eventForElastic.setOrganizer(new OrganizerForElasticDTO(museumEvent.getOrganizer().getFirstName(), museumEvent.getOrganizer().getLastName()));


        return eventForElastic;
    }

}
