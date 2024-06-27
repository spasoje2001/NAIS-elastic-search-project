package com.veljko121.backend.service.impl;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.veljko121.backend.model.RoomReservation;
import com.veljko121.backend.service.IRoomReservationService;
import com.veljko121.backend.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.veljko121.backend.core.service.impl.CRUDService;
import com.veljko121.backend.model.Room;
import com.veljko121.backend.repository.RoomRepository;
import com.veljko121.backend.service.IRoomService;

@Service
public class RoomService extends CRUDService<Room, Integer> implements IRoomService {

    private final RoomRepository roomRepository;

    @Autowired
    private IRoomReservationService roomReservationService;

    public RoomService(RoomRepository repository) {
        super(repository);
        this.roomRepository = repository;
    }

    
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> findAvailableRooms(Date startDate, Date endDate) {
        LocalDateTime startDateTime = startDate.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endDateTime = endDate.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();

        // Use the RoomReservationService to find available rooms
        Collection<Room> availableRooms = roomReservationService.findAvailableRoomsBetweenDates(startDateTime, endDateTime);

        // Convert to List if necessary or directly return the collection
        return new ArrayList<>(availableRooms);
    }

    
}
