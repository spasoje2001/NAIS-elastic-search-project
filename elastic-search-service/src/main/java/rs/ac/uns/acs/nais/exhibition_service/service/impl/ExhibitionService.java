package rs.ac.uns.acs.nais.exhibition_service.service.impl;

import org.springframework.stereotype.Service;

import rs.ac.uns.acs.nais.exhibition_service.core.service.impl.CRUDService;
import rs.ac.uns.acs.nais.exhibition_service.model.Exhibition;
import rs.ac.uns.acs.nais.exhibition_service.repository.ExhibitionRepository;
import rs.ac.uns.acs.nais.exhibition_service.service.IExhibitionService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExhibitionService extends CRUDService<Exhibition, String> implements IExhibitionService {

    private final ExhibitionRepository exhibitionRepository;
    public ExhibitionService(ExhibitionRepository exhibitionRepository) {
        super(exhibitionRepository);
        this.exhibitionRepository = exhibitionRepository;
    }

    public List<Exhibition> findOpenExhibitionsWithHighAttendance(int minTicketsSold, int minDailyAverage) {
        //return exhibitionRepository.findOpenExhibitionsWithHighAttendance(minTicketsSold);

        List<Exhibition> openExhibitions = exhibitionRepository.findOpenExhibitionsWithHighAttendance(minTicketsSold);

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

}
