package rs.ac.uns.acs.nais.exhibition_service.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import rs.ac.uns.acs.nais.exhibition_service.dto.ExhibitionRequestDTO;
import rs.ac.uns.acs.nais.exhibition_service.dto.ExhibitionResponseDTO;
import rs.ac.uns.acs.nais.exhibition_service.model.Exhibition;
import rs.ac.uns.acs.nais.exhibition_service.service.IExhibitionService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "exhibitions")
public class ExhibitionController {

    private final IExhibitionService exhibitionService;

    private final ModelMapper modelMapper;

    @GetMapping
    public List<ExhibitionResponseDTO> findAll() {
        var exhibitions = convertToList(this.exhibitionService.findAll());
        var dtos = exhibitions.stream().map(song -> modelMapper.map(song, ExhibitionResponseDTO.class)).collect(Collectors.toList());
        return dtos;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        try {
            var exhibition = exhibitionService.findById(id);
            var dto = modelMapper.map(exhibition, ExhibitionResponseDTO.class);
            return ResponseEntity.ok().body(dto);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public void addExhibition(@RequestBody ExhibitionRequestDTO request) {
        var exhibition = modelMapper.map(request, Exhibition.class);
        exhibitionService.save(exhibition);
    }

    @DeleteMapping("{id}")
    public void deleteExhibition(@PathVariable String id) {
        exhibitionService.deleteById(id);
    }

    private List<Exhibition> convertToList(Iterable<Exhibition> exhibitions) {
        List<Exhibition> exhibitionsList = new ArrayList<>();
        exhibitions.forEach(exhibitionsList::add);
        return exhibitionsList;
    }
    
}
