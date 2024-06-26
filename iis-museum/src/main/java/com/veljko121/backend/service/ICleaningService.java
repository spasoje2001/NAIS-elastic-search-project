package com.veljko121.backend.service;

import java.util.List;

import com.veljko121.backend.core.service.ICRUDService;
import com.veljko121.backend.model.Cleaning;

public interface ICleaningService extends ICRUDService<Cleaning, Integer> {

    void addCleaningToItem(Integer itemId, Cleaning cleaning);
    Cleaning save(Integer itemId, Cleaning cleaning ,Integer restaurateurId);
    Cleaning declineCleaning(Integer cleaningId, Integer curatorId, String denialReason);
    Cleaning acceptCleaning(Integer cleaningId, Integer curatorId);
    List<Cleaning> getAllNewCleanings();
    Cleaning putItemToCleaning(Integer cleaningId);
    Cleaning finishleaning(Integer cleaningId);
    void delete(Cleaning cleaning);

}
