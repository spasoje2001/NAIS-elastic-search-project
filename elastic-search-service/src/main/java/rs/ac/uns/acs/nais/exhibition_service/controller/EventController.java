package rs.ac.uns.acs.nais.exhibition_service.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import rs.ac.uns.acs.nais.exhibition_service.dto.EventRequestDTO;
import rs.ac.uns.acs.nais.exhibition_service.dto.EventResponseDTO;
import rs.ac.uns.acs.nais.exhibition_service.dto.OrganizerAverageRatingDTO;
import rs.ac.uns.acs.nais.exhibition_service.model.MuseumEvent;
import rs.ac.uns.acs.nais.exhibition_service.service.IEventService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "events")
public class EventController {

    private final IEventService eventService;

    private final ModelMapper modelMapper;

    @GetMapping
    public List<EventResponseDTO> findAll() {
        var events = convertToList(this.eventService.findAll());
        var dtos = events.stream().map(song -> modelMapper.map(song, EventResponseDTO.class)).collect(Collectors.toList());
        return dtos;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        try {
            var exhibition = eventService.findById(id);
            var dto = modelMapper.map(exhibition, EventResponseDTO.class);
            return ResponseEntity.ok().body(dto);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/average-rating")
    public List<OrganizerAverageRatingDTO> findAverageRatingByOrganizer(
            @RequestParam double minPrice,
            @RequestParam(required = false) String searchText) {
        return eventService.findAverageRatingByOrganizer(minPrice, searchText);
    }


    @PostMapping
    public void addEvent(@RequestBody EventRequestDTO request) {
        var event = modelMapper.map(request, MuseumEvent.class);
        eventService.save(event);
    }

    @DeleteMapping("{id}")
    public void deleteEvent(@PathVariable String id) {
        eventService.deleteById(id);
    }


    @PutMapping("{id}")
    public void updateEvent(@PathVariable String id, @RequestBody EventRequestDTO request) {
        var event = modelMapper.map(request, MuseumEvent.class);
        eventService.update(id, event);
    }


    private List<MuseumEvent> convertToList(Iterable<MuseumEvent> events) {
        List<MuseumEvent> eventsList = new ArrayList<>();
        events.forEach(eventsList::add);
        return eventsList;
    }
    
}
