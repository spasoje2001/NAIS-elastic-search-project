package com.veljko121.backend.service.impl.tours;

import com.veljko121.backend.core.service.impl.CRUDService;
import com.veljko121.backend.model.tours.PersonalTourRequest;
import com.veljko121.backend.model.tours.Tour;
import com.veljko121.backend.repository.tours.PersonalTourRequestRepository;
import com.veljko121.backend.service.tours.IPersonalTourRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonalTourRequestService extends CRUDService<PersonalTourRequest, Integer> implements IPersonalTourRequestService{

    @Autowired
    private final PersonalTourRequestRepository personalTourRequestRepository;

    public PersonalTourRequestService(JpaRepository<PersonalTourRequest, Integer> repository,
                                      PersonalTourRequestRepository personalTourRequestRepository) {
        super(repository);
        this.personalTourRequestRepository = personalTourRequestRepository;
    }

    @Override
    public PersonalTourRequest save(PersonalTourRequest request) {
        return personalTourRequestRepository.save(request);
    }

    @Override
    public PersonalTourRequest update(PersonalTourRequest request) {
        return personalTourRequestRepository.save(request);
    }

    public List<PersonalTourRequest> findByGuestId(Integer guestId){
        return personalTourRequestRepository.findByGuestId(guestId);
    }

    @Override
    public List<PersonalTourRequest> findAll(){
        return personalTourRequestRepository.findAll();
    }

    public List<PersonalTourRequest> findInProgress(){
        return personalTourRequestRepository.findInProgress();
    }

    public List<PersonalTourRequest> findRequestsForPreviousMonth() {
        YearMonth previousMonth = YearMonth.now().minusMonths(1);
        LocalDate startDate = previousMonth.atDay(1);
        LocalDate endDate = previousMonth.atEndOfMonth();

        List<PersonalTourRequest> requests = new ArrayList<>();
        List<PersonalTourRequest> allRequests = personalTourRequestRepository.findAll();
        for(PersonalTourRequest request : allRequests){
            if(request.getOccurrenceDateTime().toLocalDate().isBefore(endDate) &&
                    request.getOccurrenceDateTime().toLocalDate().isAfter(startDate)){
                requests.add(request);
            }
        }
        return requests;
    }

    public List<PersonalTourRequest> findByOrganizerId(Integer organizerId){
        return personalTourRequestRepository.findByOrganizerId(organizerId);
    }

    @Override
    public void delete(PersonalTourRequest request){
        personalTourRequestRepository.delete(request);
    }

}
