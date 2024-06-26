package com.veljko121.backend.service;

import com.veljko121.backend.core.service.ICRUDService;
import com.veljko121.backend.dto.ExhibitionProposalDTO;
import com.veljko121.backend.model.Exhibition;

import reactor.core.publisher.Mono;

import java.util.List;

public interface IExhibitionService extends ICRUDService<Exhibition, Integer> {
    List<Exhibition> findAll();
    Exhibition proposeExhibition(ExhibitionProposalDTO proposalDTO);
    Mono<Exhibition> createExhibition(ExhibitionProposalDTO proposalDTO);
    List<Exhibition> getExhibitionsForPreviousMonth();
    List<Exhibition> getExhibitionsForPreviousYear(Integer curatorId);

}
