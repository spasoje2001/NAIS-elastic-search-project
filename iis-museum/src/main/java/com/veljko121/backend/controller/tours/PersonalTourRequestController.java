package com.veljko121.backend.controller.tours;

import com.veljko121.backend.core.enums.PersonalTourRequestStatus;
import com.veljko121.backend.core.service.IJwtService;
import com.veljko121.backend.dto.tours.PersonalTourRequestCreateDTO;
import com.veljko121.backend.dto.tours.PersonalTourRequestResponseDTO;
import com.veljko121.backend.dto.tours.PersonalTourRequestUpdateDTO;
import com.veljko121.backend.model.Exhibition;
import com.veljko121.backend.model.tours.PersonalTourRequest;
import com.veljko121.backend.service.IExhibitionService;
import com.veljko121.backend.service.IGuestService;
import com.veljko121.backend.service.IOrganizerService;
import com.veljko121.backend.service.tours.IPersonalTourRequestService;
import com.veljko121.backend.service.tours.ITourService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/personalTourRequests")
@RequiredArgsConstructor
public class PersonalTourRequestController {

    private final IPersonalTourRequestService personalTourRequestService;
    private final IGuestService guestService;
    private final IOrganizerService organizerService;
    private final IExhibitionService exhibitionService;
    private final IJwtService jwtService;

    private final ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<?> create(@RequestBody PersonalTourRequestCreateDTO requestDTO) {
        var exhibitionDTOs = requestDTO.getExhibitions();
        // mora zbog mapiranja
        requestDTO.setExhibitions(null);
        PersonalTourRequest request = modelMapper.map(requestDTO, PersonalTourRequest.class);

        List<Exhibition> fetchedExhibitions = exhibitionDTOs.stream()
                .map(exhibition -> exhibitionService.findById(exhibition.getId()))
                .collect(Collectors.toList());
        request.setExhibitions(fetchedExhibitions);

        Integer id = jwtService.getLoggedInUserId();
        request.setProposer(guestService.findById(id));
        request.setStatus(PersonalTourRequestStatus.IN_PROGRESS);

        personalTourRequestService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(requestDTO);
    }

    @PutMapping
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<?> update(@RequestBody PersonalTourRequestUpdateDTO requestDTO) {
        var request = personalTourRequestService.findById(requestDTO.getId());
        request.setGuestNumber(requestDTO.getGuestNumber());
        request.setOccurrenceDateTime(requestDTO.getOccurrenceDateTime());
        request.setProposerContactPhone(requestDTO.getProposerContactPhone());

        var exhibitionDTOs = requestDTO.getExhibitions();
        List<Exhibition> fetchedExhibitions = exhibitionDTOs.stream()
                .map(exhibition -> exhibitionService.findById(exhibition.getId()))
                .collect(Collectors.toList());
        request.setExhibitions(fetchedExhibitions);

        personalTourRequestService.update(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(requestDTO);
    }

    @PutMapping("/handle")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<?> handle(@RequestBody PersonalTourRequestUpdateDTO requestDTO) {
        var request = personalTourRequestService.findById(requestDTO.getId());

        var id = jwtService.getLoggedInUserId();
        request.setOrganizer(organizerService.findById(id));
        request.setStatus(requestDTO.getStatus());
        request.setDenialReason(requestDTO.getDenialReason());

        personalTourRequestService.update(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(requestDTO);
    }

    @GetMapping("/{guestId}")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<?> findByGuestId(@PathVariable Integer guestId) {
        List<PersonalTourRequest> requests = personalTourRequestService.findByGuestId(guestId);
        var requestResponse = requests.stream()
                .map(request -> modelMapper.map(request, PersonalTourRequestResponseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(requestResponse);
    }

    @GetMapping
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<?> findAll() {
        List<PersonalTourRequest> requests = personalTourRequestService.findAll();
        var requestResponse = requests.stream()
                .map(request -> modelMapper.map(request, PersonalTourRequestResponseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(requestResponse);
    }

    @GetMapping("/onHold")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<?> findOnHold() {
        List<PersonalTourRequest> requests = personalTourRequestService.findInProgress();
        var requestResponse = requests.stream()
                .map(request -> modelMapper.map(request, PersonalTourRequestResponseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(requestResponse);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        PersonalTourRequest request = personalTourRequestService.findById(id);

        if (request != null) {
            personalTourRequestService.delete(request);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
