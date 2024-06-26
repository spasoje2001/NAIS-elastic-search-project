package rs.ac.uns.acs.nais.exhibition_service.service;

import rs.ac.uns.acs.nais.exhibition_service.core.service.ICRUDService;
import rs.ac.uns.acs.nais.exhibition_service.model.Exhibition;

import java.util.List;

public interface IExhibitionService extends ICRUDService<Exhibition, String> {

    public List<Exhibition> findOpenExhibitionsWithHighAttendance(int minTicketsSold, int minDailyAverage, String theme);

    public List<Exhibition> findByDescriptionAndDateRangeAndMinTicketsSold(String searchText, String minStartDate, String maxStartDate, int minTicketsSold);

    public List<Exhibition> findByReviewTextAndThemeAndMinAverageRating(String reviewText, String theme, double minAverageRating);

    public List<Exhibition> findByPeriodTextAndMinTicketsSoldAndStatus(String periodText, int minTicketsSold, String status);

    public Double getAverageTicketPriceByCategoryAndDescriptionAndStatus(String category, String description, String status);

}
