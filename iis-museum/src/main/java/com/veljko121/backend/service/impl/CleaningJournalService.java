package com.veljko121.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.veljko121.backend.core.service.impl.CRUDService;
import com.veljko121.backend.model.CleaningJournal;
import com.veljko121.backend.repository.CleaningJournalRepository;
import com.veljko121.backend.service.ICleaningJournalService;

@Service
public class CleaningJournalService  extends CRUDService<CleaningJournal, Integer> implements ICleaningJournalService{
 
    private final CleaningJournalRepository cleaningJournalRepository;

    @Autowired
    public CleaningJournalService(CleaningJournalRepository repository, CleaningJournalRepository cleaningJournalRepository) {
        super(repository);
        this.cleaningJournalRepository = cleaningJournalRepository;
    }

    @Override
    public List<CleaningJournal> findAll() {
        return cleaningJournalRepository.findAll();
    }
}
