package com.veljko121.backend.service.tours;

import com.veljko121.backend.core.service.ICRUDService;
import com.veljko121.backend.model.tours.PersonalTourRequestJournal;

import java.util.List;

public interface IPersonalTourRequestJournalService extends ICRUDService<PersonalTourRequestJournal, Integer> {
    List<PersonalTourRequestJournal> findAll();
}
