package com.veljko121.backend.service.impl.tours;

import com.veljko121.backend.core.service.impl.CRUDService;
import com.veljko121.backend.model.tours.PersonalTourRequestJournal;
import com.veljko121.backend.repository.tours.PersonalTourRequestJournalRepository;
import com.veljko121.backend.service.tours.IPersonalTourRequestJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalTourRequestJournalService extends CRUDService<PersonalTourRequestJournal, Integer> implements IPersonalTourRequestJournalService  {

    @Autowired
    private final PersonalTourRequestJournalRepository journalRepository;

    public PersonalTourRequestJournalService(JpaRepository<PersonalTourRequestJournal, Integer> repository,
                                      PersonalTourRequestJournalRepository journalRepository) {
        super(repository);
        this.journalRepository = journalRepository;
    }

    @Override
    public List<PersonalTourRequestJournal> findAll(){
        return journalRepository.findAll();
    }

}
