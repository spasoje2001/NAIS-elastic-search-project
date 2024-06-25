package com.veljko121.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.veljko121.backend.model.Room;


public interface RoomRepository extends JpaRepository<Room, Integer>{
    
}
