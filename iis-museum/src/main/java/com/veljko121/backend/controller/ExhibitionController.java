package com.veljko121.backend.controller;

import com.veljko121.backend.dto.ExhibitionProposalDTO;
import com.veljko121.backend.dto.ExhibitionResponseDTO;
import com.veljko121.backend.mapper.ExhibitionMapper;
import com.veljko121.backend.model.Exhibition;
import com.veljko121.backend.service.IExhibitionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/exhibitions")
@RequiredArgsConstructor
public class ExhibitionController {

    private final IExhibitionService exhibitionService;
    private final ModelMapper modelMapper;
    private final ExhibitionMapper exhibitionMapper;

    @GetMapping(path = "{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        var exhibition = exhibitionService.findById(id);
        var exhibitionResponse = exhibitionMapper.mapToDTO(exhibition);
        return ResponseEntity.ok().body(exhibitionResponse);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        var exhibitions = exhibitionService.findAll();
        var response = exhibitions.stream()
                .map(exhibitionMapper::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/propose")
    public ResponseEntity<?> proposeExhibition(@RequestBody @Valid Mono<ExhibitionProposalDTO> mono) {
        try {
            var response = mono.flatMap(exhibitionService::createExhibition);
            // Exhibition createdExhibition = exhibitionService.proposeExhibition(mono);
            // ExhibitionResponseDTO exhibitionDTO = exhibitionMapper.mapToDTO(createdExhibition);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // In a real-world application, you might want to log this exception and return a user-friendly message
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Generic exception handling, likely you'd want more specific handling
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
