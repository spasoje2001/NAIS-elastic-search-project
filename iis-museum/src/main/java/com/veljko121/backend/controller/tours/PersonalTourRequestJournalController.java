package com.veljko121.backend.controller.tours;

import com.veljko121.backend.dto.tours.PersonalTourRequestJournalResponseDTO;
import com.veljko121.backend.model.tours.PersonalTourRequestJournal;
import com.veljko121.backend.service.tours.IPersonalTourRequestJournalService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/personalTourRequestsJournal")
@RequiredArgsConstructor
public class PersonalTourRequestJournalController {

    private final IPersonalTourRequestJournalService journalService;

    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<PersonalTourRequestJournal> requests = journalService.findAll();
        var requestResponse = requests.stream()
                .map(request -> modelMapper.map(request, PersonalTourRequestJournalResponseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(requestResponse);
    }

}
