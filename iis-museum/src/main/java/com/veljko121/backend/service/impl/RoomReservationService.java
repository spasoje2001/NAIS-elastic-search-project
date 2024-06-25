package com.veljko121.backend.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.veljko121.backend.core.service.impl.CRUDService;
import com.veljko121.backend.model.Room;
import com.veljko121.backend.model.RoomReservation;
import com.veljko121.backend.model.events.Event;
import com.veljko121.backend.repository.RoomRepository;
import com.veljko121.backend.repository.RoomReservationRepository;
import com.veljko121.backend.service.IRoomReservationService;

@Service
public class RoomReservationService extends CRUDService<RoomReservation, Integer> implements IRoomReservationService {

    private final RoomReservationRepository roomReservationRepository;
    private final RoomRepository roomRepository;
    
    public RoomReservationService(RoomReservationRepository repository, RoomRepository roomRepository) {
        super(repository);
        this.roomReservationRepository = repository;
        this.roomRepository = roomRepository;
    }

    private Boolean overlaps(RoomReservation reservation, LocalDateTime startDateTime, Integer durationMinutes) {
        var reservationStartDateTime = reservation.getStartDateTime();
        var reservationEndDateTime = reservation.getEndDateTime();
        var endDateTime = startDateTime.plusMinutes(durationMinutes);

        if ((startDateTime.isAfter(reservationStartDateTime) || startDateTime.isEqual(reservationStartDateTime)) && startDateTime.isBefore(reservationEndDateTime) ||
            (endDateTime.isAfter(reservationStartDateTime)) && (endDateTime.isBefore(reservationEndDateTime) || endDateTime.isEqual(reservationEndDateTime))) {
            return true;
        }

        return false;
    }

    @Override
    public Boolean isRoomAvailable(Room room, LocalDateTime startDateTime, Integer durationMinutes) {
        for (var reservation : roomReservationRepository.findByRoom(room)) {
            if (overlaps(reservation, startDateTime, durationMinutes)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Collection<Room> findAvailableRoomsByTimespan(LocalDateTime startDateTime, Integer durationMinutes) {
        var availableRooms = new ArrayList<Room>();
        for (var room : roomRepository.findAll()) {
            if (isRoomAvailable(room, startDateTime, durationMinutes)) availableRooms.add(room);
        }
        return availableRooms;
    }

    @Override
    public Collection<Room> findAvailableRoomsForUpdating(Event event, LocalDateTime startDateTime, Integer durationMinutes) {
        var availableRooms = new ArrayList<Room>();
        for (var room : roomRepository.findAll()) {
            if (isRoomAvailableForUpdating(room, startDateTime, durationMinutes, event.getRoomReservation())) availableRooms.add(room);
        }
        return availableRooms;
    }

    @Override
    public Boolean isRoomAvailableForUpdating(Room room, LocalDateTime startDateTime, Integer durationMinutes, RoomReservation roomReservation) {
        var reservations = roomReservationRepository.findByRoom(room);
        var filtered = new ArrayList<RoomReservation>();
        for (var reservation : reservations) {
            if (reservation.getId() != roomReservation.getId()) filtered.add(reservation);
        }
        for (var reservation : filtered) {
            if (overlaps(reservation, startDateTime, durationMinutes)) {
                return false;
            }
        }
        return true;
    }

    public Collection<Room> findAvailableRoomsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        Set<Room> availableRooms = new HashSet<>(); // Use a Set to avoid duplicates
        for (var room : roomRepository.findAll()) {
            boolean isAvailable = true;
            for (var reservation : roomReservationRepository.findByRoom(room)) {
                if (startDate.isBefore(reservation.getEndDateTime()) &&
                        endDate.isAfter(reservation.getStartDateTime())) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable) {
                availableRooms.add(room); // HashSet will only add unique rooms
            }

        }
        return availableRooms; // Return the Set, which contains only distinct rooms
    }
    
}
