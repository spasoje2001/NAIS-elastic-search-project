package com.veljko121.backend.service.tours;

import com.veljko121.backend.core.service.ICRUDService;
import com.veljko121.backend.model.tours.PersonalTourRequest;

import java.util.List;

public interface IPersonalTourRequestService extends ICRUDService<PersonalTourRequest, Integer> {

    PersonalTourRequest update(PersonalTourRequest request);

    List<PersonalTourRequest> findByGuestId(Integer guestId);

    List<PersonalTourRequest> findAll();

    List<PersonalTourRequest> findInProgress();

    List<PersonalTourRequest> findRequestsForPreviousMonth();

    List<PersonalTourRequest> findByOrganizerId(Integer organizerId);
}
