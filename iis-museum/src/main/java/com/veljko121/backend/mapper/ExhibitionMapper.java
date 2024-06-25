package com.veljko121.backend.mapper;

import com.veljko121.backend.dto.*;
import com.veljko121.backend.model.*;
import com.veljko121.backend.util.DateUtil;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExhibitionMapper {



    public ExhibitionResponseDTO mapToDTO(Exhibition exhibition) {
        ExhibitionResponseDTO dto = new ExhibitionResponseDTO();
        dto.setId(exhibition.getId());
        dto.setName(exhibition.getName());
        dto.setPicture(exhibition.getPicture());
        dto.setShortDescription(exhibition.getShortDescription());
        dto.setLongDescription(exhibition.getLongDescription());
        dto.setTheme(exhibition.getTheme());
        dto.setStatus(exhibition.getStatus());
        dto.setStartDate(DateUtil.dateToString(exhibition.getStartDate()));
        dto.setEndDate(DateUtil.dateToString(exhibition.getEndDate()));
        dto.setPrice(exhibition.getPrice());
        dto.setOrganizer(mapToOrganizerDTO(exhibition.getOrganizer()));
        dto.setCurator(mapToCuratorDTO(exhibition.getCurator()));
        dto.setRoomReservation(mapToRoomReservationDTO(exhibition.getRoomReservation()));
        // Map roomReservations and other properties as needed
        // List<RoomReservationResponseDTO> roomReservationsDTO = exhibition.getRoomReservations()
        //         .stream()
        //         .map(this::mapToRoomReservationDTO)
        //         .collect(Collectors.toList());
        // dto.setRoomReservations(roomReservationsDTO);
        return dto;
    }


    private OrganizerResponseDTO mapToOrganizerDTO(Organizer organizer) {
        if (organizer == null) {
            return null; // Return null if the input is null
        }
        OrganizerResponseDTO organizerDTO = new OrganizerResponseDTO();
        organizerDTO.setId(organizer.getId());
        organizerDTO.setUsername(organizer.getUsername());
        organizerDTO.setEmail(organizer.getEmail());
        organizerDTO.setFirstName(organizer.getFirstName());
        organizerDTO.setLastName(organizer.getLastName());
        organizerDTO.setRole(organizer.getRole()); // Assuming Role is an enum
        organizerDTO.setBiography(organizer.getBiography());
        // Map organizer properties
        return organizerDTO;
    }

    private CuratorResponseDTO mapToCuratorDTO(Curator curator) {
        if (curator == null) {
            return null; // Return null if the input is null
        }
        CuratorResponseDTO curatorDTO = new CuratorResponseDTO();
        curatorDTO.setId(curator.getId());
        curatorDTO.setUsername(curator.getUsername());
        curatorDTO.setEmail(curator.getEmail());
        curatorDTO.setFirstName(curator.getFirstName());
        curatorDTO.setLastName(curator.getLastName());
        curatorDTO.setRole(curator.getRole()); // Assuming Role is an enum
        curatorDTO.setBiography(curator.getBiography());
        // Map curator properties
        return curatorDTO;
    }
    private RoomReservationResponseDTO mapToRoomReservationDTO(RoomReservation roomReservation) {
        if (roomReservation == null) {
            return null; // Return null if the input is null
        }
        RoomReservationResponseDTO roomReservationDTO = new RoomReservationResponseDTO();
        roomReservationDTO.setId(roomReservation.getId());
        roomReservationDTO.setStartDateTime(roomReservation.getStartDateTime());
        roomReservationDTO.setEndDateTime(roomReservation.getEndDateTime());
        roomReservationDTO.setRoom(mapToRoomDTO(roomReservation.getRoom())); // Assuming method mapToRoomDTO exists
        // Map other properties as needed
        return roomReservationDTO;
    }

    private RoomResponseDTO mapToRoomDTO(Room room) {
        if (room == null) {
            return null; // Return null if the input is null
        }
        RoomResponseDTO roomDTO = new RoomResponseDTO();
        roomDTO.setId(room.getId());
        roomDTO.setName(room.getName());
        roomDTO.setFloor(room.getFloor());
        roomDTO.setNumber(room.getNumber());
        // Map other properties as needed
        return roomDTO;
    }


}
