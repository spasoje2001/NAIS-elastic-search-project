package com.veljko121.backend.controller.tours;

import com.veljko121.backend.dto.tours.*;
import com.veljko121.backend.model.Exhibition;
import com.veljko121.backend.model.tours.Tour;
import com.veljko121.backend.model.tours.TourPricelist;
import com.veljko121.backend.service.tours.ITourPricelistService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tourPricelists")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ORGANIZER')")
public class TourPricelistController {

    private final ITourPricelistService tourPricelistService;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TourPricelistCreateDTO tourPricelistCreateDTO) {
        var tourPricelist = modelMapper.map(tourPricelistCreateDTO, TourPricelist.class);

        tourPricelistService.save(tourPricelist);

        return ResponseEntity.status(HttpStatus.CREATED).body(tourPricelistCreateDTO);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ORGANIZER', 'GUEST')")
    public ResponseEntity<?> findById() {
        TourPricelist tourPricelist = tourPricelistService.findById(0);

        var tourPricelistResponse = modelMapper.map(tourPricelist, TourPricelistResponseDTO.class);

        return ResponseEntity.ok().body(tourPricelistResponse);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody TourPricelistUpdateDTO tourPricelistUpdateDTO) {
        var tourPricelist = modelMapper.map(tourPricelistUpdateDTO, TourPricelist.class);

        tourPricelistService.update(tourPricelist);

        return ResponseEntity.status(HttpStatus.CREATED).body(tourPricelistUpdateDTO);
    }
}
