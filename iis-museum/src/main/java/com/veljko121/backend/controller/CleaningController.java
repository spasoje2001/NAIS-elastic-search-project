package com.veljko121.backend.controller;

import java.util.List;

import com.veljko121.backend.dto.CleaningDeclineDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.veljko121.backend.core.service.IJwtService;
import com.veljko121.backend.dto.CleaningCreateDTO;
import com.veljko121.backend.model.Cleaning;
import com.veljko121.backend.model.CleaningJournal;
import com.veljko121.backend.model.tours.PersonalTourRequest;
import com.veljko121.backend.service.ICleaningJournalService;
import com.veljko121.backend.service.ICleaningService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cleaning")
@RequiredArgsConstructor
public class CleaningController {
    
    private final ModelMapper modelMapper;
    private final ICleaningService cleaningService;
    private final IJwtService jwtService;
    private final ICleaningJournalService cleaningJournalService;

     // POST method to add a cleaning to an item
    @PostMapping("/{itemId}")
    public ResponseEntity<Cleaning> addCleaningToItem(@PathVariable Integer itemId, @RequestBody CleaningCreateDTO cleaningDTO) {
        var cleaning = modelMapper.map(cleaningDTO, Cleaning.class);
        Integer userId = jwtService.getLoggedInUserId();
        Cleaning savedCleaning = cleaningService.save(itemId, cleaning,userId);
        return new ResponseEntity<>(savedCleaning, HttpStatus.CREATED);
    }

    // PUT method to decline cleaning for an item
    @PutMapping("/decline")
    public ResponseEntity<Void> declineCleaning(@RequestBody CleaningDeclineDTO cleaningDeclineDTO) {
        cleaningService.declineCleaning(cleaningDeclineDTO.getCleaningId(), cleaningDeclineDTO.getCuratorId(), cleaningDeclineDTO.getDenialExplanation());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // PUT method to approve cleaning for an item
    @PutMapping("/approve/{cleaningId}/{curatorId}")
    public ResponseEntity<Void> approveCleaning(@PathVariable Integer cleaningId, @PathVariable Integer curatorId) {
        cleaningService.acceptCleaning(cleaningId, curatorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/new")
    public List<Cleaning> getAllNewCleanings() {
        return cleaningService.getAllNewCleanings();
    }

    @PutMapping("/putToCleaning/{cleaningId}")
    public ResponseEntity<Void> putItemToCleaning(@PathVariable Integer cleaningId) {
        cleaningService.putItemToCleaning(cleaningId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/finishCleaning/{cleaningId}")
    public ResponseEntity<Void> finishCleaning(@PathVariable Integer cleaningId) {
        cleaningService.finishleaning(cleaningId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/journal")
    public List<CleaningJournal> getAllJournal() {
        return cleaningJournalService.findAll();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Cleaning> deleteCleaning(@PathVariable Integer id) {
         Cleaning cleaning = cleaningService.findById(id);

        if (cleaning != null) {
            cleaningService.delete(cleaning);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    
    
}
