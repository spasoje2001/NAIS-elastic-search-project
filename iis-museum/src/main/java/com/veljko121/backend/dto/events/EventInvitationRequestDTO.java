package com.veljko121.backend.dto.events;

import java.util.Collection;

import lombok.Data;

@Data
public class EventInvitationRequestDTO {
    
    private Collection<Integer> curatorIds;
    
}
