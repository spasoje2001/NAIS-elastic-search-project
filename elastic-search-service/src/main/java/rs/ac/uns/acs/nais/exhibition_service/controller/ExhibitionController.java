package rs.ac.uns.acs.nais.exhibition_service.controller;

import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/high-attendance")
    public List<ExhibitionResponseDTO> findOpenExhibitionsWithHighAttendance(
            @RequestParam(name = "minTicketsSold") int minTicketsSold,
            @RequestParam(name = "minDailyTicketsSold") int minDailyAverage,
            @RequestParam(name = "theme", required = true) String theme)
    {
        var exhibitions = convertToList(exhibitionService.findOpenExhibitionsWithHighAttendance(minTicketsSold, minDailyAverage, theme));
        return exhibitions.stream().map(exhibition -> modelMapper.map(exhibition, ExhibitionResponseDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/search-by-description-startDate-ticketsSold")
    public List<ExhibitionResponseDTO> findByDescriptionAndDateRangeAndMinTicketsSold(
            @RequestParam(name = "searchText") String searchText,
            @RequestParam(name = "minStartDate") String minStartDate,
            @RequestParam(name = "maxStartDate") String maxStartDate,
            @RequestParam(name = "minTicketsSold") int minTicketsSold) {

        List<Exhibition> exhibitions = exhibitionService.findByDescriptionAndDateRangeAndMinTicketsSold(searchText, minStartDate, maxStartDate, minTicketsSold);
        return exhibitions.stream()
                .map(exhibition -> modelMapper.map(exhibition, ExhibitionResponseDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/search-by-review-text-theme-min-average-rating")
    public List<ExhibitionResponseDTO> findByReviewTextAndCategoryAndMinRating(
            @RequestParam(name = "reviewText") String reviewText,
            @RequestParam(name = "theme") String theme,
            @RequestParam(name = "minAverageRating") double minAverageRating) {

        List<Exhibition> exhibitions = exhibitionService.findByReviewTextAndThemeAndMinAverageRating(reviewText, theme, minAverageRating);
        return exhibitions.stream()
                .map(exhibition -> modelMapper.map(exhibition, ExhibitionResponseDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/by-period-ticketsSold-status")
    public List<ExhibitionResponseDTO> findByPeriodTextAndMinTicketsSoldAndStatus(
            @RequestParam(name = "itemPeriod") String periodText,
            @RequestParam(name = "minTicketsSold") int minTicketsSold,
            @RequestParam(name = "status") String status) {

        List<Exhibition> exhibitions = exhibitionService.findByPeriodTextAndMinTicketsSoldAndStatus(periodText, minTicketsSold, status);

        return exhibitions.stream()
                .map(exhibition -> modelMapper.map(exhibition, ExhibitionResponseDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/average-ticket-price-for-item-category")
    public Double averageTicketPriceByCategoryAndDescriptionAndStatus(
            @RequestParam(name = "itemCategory") String category,
            @RequestParam(name = "itemDescription") String description,
            @RequestParam(name = "status") String status) {

        if (description != null && !description.isBlank() && status != null && !status.isBlank()) {
            return exhibitionService.getAverageTicketPriceByCategoryAndDescriptionAndStatus(category, description, status);
        } else {
            return null;
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
