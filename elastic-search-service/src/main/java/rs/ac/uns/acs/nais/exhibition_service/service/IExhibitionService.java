package rs.ac.uns.acs.nais.exhibition_service.service;

import rs.ac.uns.acs.nais.exhibition_service.core.service.ICRUDService;
import rs.ac.uns.acs.nais.exhibition_service.model.Exhibition;

import java.util.List;

public interface IExhibitionService extends ICRUDService<Exhibition, String> {

    public List<Exhibition> findOpenExhibitionsWithHighAttendance(int minTicketsSold, int minDailyAverage);

}
