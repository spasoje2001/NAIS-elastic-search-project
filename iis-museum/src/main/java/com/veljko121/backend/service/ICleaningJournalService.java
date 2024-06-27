package com.veljko121.backend.service;

import java.util.List;

import com.veljko121.backend.core.service.ICRUDService;
import com.veljko121.backend.model.CleaningJournal;

public interface ICleaningJournalService extends ICRUDService<CleaningJournal, Integer>{

    List<CleaningJournal> findAll();
}
