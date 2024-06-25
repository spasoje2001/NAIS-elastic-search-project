package com.veljko121.backend.core.exception;

import com.veljko121.backend.model.RoomReservation;

public class RoomNotAvailableException extends RuntimeException {

    public RoomNotAvailableException() {
        super("Room not available.");
    }

    public RoomNotAvailableException(RoomReservation roomReservation) {
        super("Room " + roomReservation.getRoom().getName() + " not available in the given time period: " + roomReservation.getStartDateTime() + " - " + roomReservation.getEndDateTime());
    }
    
}
