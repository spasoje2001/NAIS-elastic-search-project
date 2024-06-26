package com.veljko121.backend.dto.events;

import java.time.LocalDateTime;

import com.veljko121.backend.core.enums.EventInvitationStatus;
import com.veljko121.backend.dto.CuratorResponseDTO;

import lombok.Data;

@Data
public class EventInvitationResponseDTO {
    
    private Integer id;
    private EventResponseDTO event;
    private CuratorResponseDTO curator;
    private EventInvitationStatus status;
    private String declinationExplanation;
    private LocalDateTime createdDateTime;
    
}
