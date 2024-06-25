package com.veljko121.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veljko121.backend.model.Room;
import com.veljko121.backend.model.RoomReservation;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Integer> {

    List<RoomReservation> findByRoom(Room room);

    List<RoomReservation> findByRoomId(Integer roomId);

 }
