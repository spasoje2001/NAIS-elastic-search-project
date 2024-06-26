package rs.ac.uns.acs.nais.exhibition_service.service.impl;

import org.springframework.stereotype.Service;

import rs.ac.uns.acs.nais.exhibition_service.core.service.impl.CRUDService;
import rs.ac.uns.acs.nais.exhibition_service.model.Exhibition;
import rs.ac.uns.acs.nais.exhibition_service.repository.ExhibitionRepository;
import rs.ac.uns.acs.nais.exhibition_service.service.IExhibitionService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class ExhibitionService extends CRUDService<Exhibition, String> implements IExhibitionService {

    private final ExhibitionRepository exhibitionRepository;
    public ExhibitionService(ExhibitionRepository exhibitionRepository) {
        super(exhibitionRepository);
        this.exhibitionRepository = exhibitionRepository;
    }

    public List<Exhibition> findOpenExhibitionsWithHighAttendance(int minTicketsSold, int minDailyAverage, String theme) {
        //return exhibitionRepository.findOpenExhibitionsWithHighAttendance(minTicketsSold);

        List<Exhibition> openExhibitions = exhibitionRepository.findOpenExhibitionsByMinTicketsSold(minTicketsSold, theme);

        // Filter exhibitions based on minDailyAverage
        List<Exhibition> filteredExhibitions = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (Exhibition exhibition : openExhibitions) {
            LocalDate startDate = exhibition.getStartDate();
            long daysBetween = ChronoUnit.DAYS.between(startDate, today);
            if (daysBetween > 0) {
                long dailyAverage = exhibition.getTicketsSold() / daysBetween;
                if (dailyAverage >= minDailyAverage) {
                    filteredExhibitions.add(exhibition);
                }
            }
        }

        return filteredExhibitions;
    }

    public List<Exhibition> findByDescriptionAndDateRangeAndMinTicketsSold(String searchText, String minStartDate, String maxStartDate, int minTicketsSold) {
        return exhibitionRepository.findByDescriptionAndDateRangeAndMinTicketsSold(searchText, minStartDate, maxStartDate, minTicketsSold);
    }

    public List<Exhibition> findByReviewTextAndThemeAndMinAverageRating(String reviewText, String theme, double minAverageRating) {
        List<Exhibition> exhibitions = exhibitionRepository.findByReviewTextAndTheme(reviewText, theme); // Fetch all exhibitions matching the review and theme criteria

        List<Exhibition> filteredExhibitions = new ArrayList<>();

        for (Exhibition exhibition : exhibitions) {
            OptionalDouble averageRating = exhibition.getReviews().stream()
                    .mapToInt(review -> review.getRating())
                    .average();

            if (averageRating.isPresent() && averageRating.getAsDouble() >= minAverageRating) {
                filteredExhibitions.add(exhibition);
            }
        }

        return filteredExhibitions;
    }

    public List<Exhibition> findByPeriodTextAndMinTicketsSoldAndStatus(String periodText, int minTicketsSold, String status) {
        return exhibitionRepository.findByPeriodTextAndMinTicketsSoldAndStatus(periodText, minTicketsSold, status);
    }

    public Double getAverageTicketPriceByCategoryAndDescriptionAndStatus(String category, String description, String status) {
        List<Exhibition> exhibitions = exhibitionRepository.findByCategoryAndItemDescriptionAndStatus(category, description, status);

        // Calculate average ticket price
        double totalExhibitionPrice = 0;
        int count = 0;
        for (Exhibition exhibition : exhibitions) {
            totalExhibitionPrice += exhibition.getPrice();
            count++;
        }

        if (count > 0) {
            return totalExhibitionPrice / count;
        } else {
            return null; // or handle as needed when no exhibitions match the criteria
        }
    }

    @Override
    public void update(String id, Exhibition exhibition) {
        var exhibitionOld = findById(id);

        exhibitionOld.setName(exhibition.getName());
        exhibitionOld.setShortDescription(exhibition.getShortDescription());
        exhibitionOld.setLongDescription(exhibition.getLongDescription());
        exhibitionOld.setStartDate(exhibition.getStartDate());
        exhibitionOld.setEndDate(exhibition.getEndDate());
        exhibitionOld.setCurator(exhibition.getCurator());
        exhibitionOld.setOrganizer(exhibition.getOrganizer());
        exhibitionOld.setPrice(exhibition.getPrice());
        exhibitionOld.setTheme(exhibition.getTheme());
        exhibitionOld.setRoom(exhibition.getRoom());

        super.save(exhibitionOld);
    }

}
