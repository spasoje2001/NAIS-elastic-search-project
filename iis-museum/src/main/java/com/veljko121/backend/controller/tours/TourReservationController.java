package com.veljko121.backend.controller.tours;

import com.veljko121.backend.core.service.IJwtService;
import com.veljko121.backend.dto.tours.TourCreateDTO;
import com.veljko121.backend.dto.tours.TourReservationCreateDTO;
import com.veljko121.backend.dto.tours.TourReservationResponseDTO;
import com.veljko121.backend.dto.tours.TourResponseDTO;
import com.veljko121.backend.model.Exhibition;
import com.veljko121.backend.model.tours.Tour;
import com.veljko121.backend.model.tours.TourReservation;
import com.veljko121.backend.service.IGuestService;
import com.veljko121.backend.service.IOrganizerService;
import com.veljko121.backend.service.tours.ITourPricelistService;
import com.veljko121.backend.service.tours.ITourReservationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tourReservations")
@RequiredArgsConstructor
public class TourReservationController {

    private final ITourReservationService tourReservationService;
    private final IJwtService jwtService;
    private final IGuestService guestService;
    private final ITourPricelistService pricelistService;

    private final ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<?> create(@RequestBody TourReservationCreateDTO reservationCreateDTO) {
        var reservation = modelMapper.map(reservationCreateDTO, TourReservation.class);

        var id = jwtService.getLoggedInUserId();
        reservation.setGuest(guestService.findById(id));

        reservation.setReservationDateTime(LocalDateTime.now());

        var pricelist = pricelistService.findById(0);
        var totalTicketPrice = Integer.parseInt(pricelist.getAdultTicketPrice()) * Integer.parseInt(reservation.getNumberOfAdultTickets())
                + Integer.parseInt(pricelist.getMinorTicketPrice()) * Integer.parseInt(reservation.getNumberOfMinorTickets());
        reservation.setTotalPrice(String.valueOf(totalTicketPrice));

        tourReservationService.save(reservation);

        return ResponseEntity.status(HttpStatus.CREATED).body(reservationCreateDTO);
    }

    @GetMapping("/guests/{guestId}")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<?> findByGuestId(@PathVariable Integer guestId) {
        List<TourReservation> reservations = tourReservationService.findByGuestId(guestId);
        var tourReservationResponse = reservations.stream()
                .map(reservation -> modelMapper.map(reservation, TourReservationResponseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(tourReservationResponse);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        TourReservation tourReservation = tourReservationService.findById(id);

        if (tourReservation != null) {
            tourReservationService.delete(tourReservation);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
