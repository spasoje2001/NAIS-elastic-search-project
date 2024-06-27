package com.veljko121.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CleaningDeclineDTO {

    Integer cleaningId;

    Integer curatorId;

    String denialExplanation;

}
