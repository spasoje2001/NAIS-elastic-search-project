package com.veljko121.backend.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.veljko121.backend.core.enums.CleaningStatus;
import com.veljko121.backend.core.service.impl.CRUDService;
import com.veljko121.backend.model.Cleaning;
import com.veljko121.backend.model.Curator;
import com.veljko121.backend.model.Item;
import com.veljko121.backend.model.Restaurateur;
import com.veljko121.backend.repository.CleaningRepository;
import com.veljko121.backend.repository.CuratorRepository;
import com.veljko121.backend.repository.ItemRepository;
import com.veljko121.backend.repository.RestaurateurRepository;
import com.veljko121.backend.service.ICleaningService;

@Service
public class CleaningService extends CRUDService<Cleaning, Integer> implements ICleaningService{

    private final CleaningRepository cleaningRepository;
    private final CuratorRepository curatorRepository;
    private final ItemRepository itemRepository;
    private final RestaurateurRepository restaurateurRepository;

    @Autowired
    public CleaningService(CleaningRepository repository, CleaningRepository cleaningRepository, ItemRepository itemRepository, CuratorRepository curatorRepository, RestaurateurRepository restaurateurRepository) {
        super(repository);
        this.cleaningRepository = cleaningRepository;
        this.itemRepository = itemRepository;
        this.curatorRepository = curatorRepository;
        this.restaurateurRepository = restaurateurRepository;
    }

    @Override
    public Cleaning save(Integer itemId, Cleaning cleaning, Integer restaurateurId) {
        Restaurateur restaurateur = restaurateurRepository.findById(restaurateurId)
        .orElseThrow(() -> new RuntimeException("Restaurateur not found with id: " + restaurateurId));

        cleaning.setRestaurateur(restaurateur);
        cleaning.setItemId(itemId);
        Cleaning savedCleaning = cleaningRepository.save(cleaning);
        addCleaningToItem(itemId, cleaning);
        return savedCleaning;
    }

    public void addCleaningToItem(Integer itemId, Cleaning cleaning) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found with id: " + itemId));
        item.setCleaning(cleaning);
        itemRepository.save(item);
    }

    public Cleaning acceptCleaning(Integer cleaningId, Integer curatorId) {
        Cleaning cleaning = cleaningRepository.findById(cleaningId)
                .orElseThrow(() -> new RuntimeException("Cleaning not found with id: " + cleaningId));

        Curator curator = curatorRepository.findById(curatorId)
                .orElseThrow(() -> new RuntimeException("Curator not found with id: " + curatorId));
        Item item = itemRepository.findById(cleaning.getItemId()).orElseThrow(() -> new RuntimeException("Item not found with id: " + cleaning.getItemId()));
        // Update the status and curator of the cleaning
        cleaning.setStatus(CleaningStatus.APPROVED);
        cleaning.setCurator(curator);
        item.getCleaning().setStatus(CleaningStatus.CLEANSED);
        // Save the updated cleaning
        return cleaningRepository.save(cleaning);
    }

    public Cleaning declineCleaning(Integer cleaningId, Integer curatorId, String denialReason) {
        Cleaning cleaning = cleaningRepository.findById(cleaningId)
                .orElseThrow(() -> new RuntimeException("Cleaning not found with id: " + cleaningId));

        Curator curator = curatorRepository.findById(curatorId)
                .orElseThrow(() -> new RuntimeException("Curator not found with id: " + curatorId));

        // Update the status and curator of the cleaning
        cleaning.setStatus(CleaningStatus.REJECTED);
        cleaning.setCurator(curator);
        cleaning.setDenialReason(denialReason);

        // Save the updated cleaning
        return cleaningRepository.save(cleaning);
    }

    public List<Cleaning> getAllNewCleanings() {
        return cleaningRepository.findAll().stream()
                .filter(cleaning -> cleaning.getStatus() == CleaningStatus.NEW)
                .collect(Collectors.toList());
    }

    public Cleaning putItemToCleaning(Integer cleaningId) {
        Cleaning cleaning = cleaningRepository.findById(cleaningId)
                .orElseThrow(() -> new RuntimeException("Cleaning not found with id: " + cleaningId));
        Item item = itemRepository.findById(cleaning.getItemId()).orElseThrow(() -> new RuntimeException("Item not found with id: " + cleaning.getItemId()));
        // Update the status and curator of the cleaning
        cleaning.setStatus(CleaningStatus.INCLEANING);
        cleaning.setPutToCleaningTime(LocalDate.now());
        item.getCleaning().setStatus(CleaningStatus.INCLEANING);
        item.getCleaning().setPutToCleaningTime(LocalDate.now());
        // Save the updated cleaning
        return cleaningRepository.save(cleaning);
    }

    public Cleaning finishleaning(Integer cleaningId) {
        Cleaning cleaning = cleaningRepository.findById(cleaningId)
                .orElseThrow(() -> new RuntimeException("Cleaning not found with id: " + cleaningId));
        Item item = itemRepository.findById(cleaning.getItemId()).orElseThrow(() -> new RuntimeException("Item not found with id: " + cleaning.getItemId()));
        // Update the status and curator of the cleaning
        cleaning.setStatus(CleaningStatus.CLEANSED);
        cleaning.setFinishCleaningTime(LocalDate.now());
        item.getCleaning().setStatus(CleaningStatus.CLEANSED);
        item.getCleaning().setFinishCleaningTime(LocalDate.now());
        // Save the updated cleaning
        return cleaningRepository.save(cleaning);
    }

    @Override
    public void delete(Cleaning cleaning){
        Item item = itemRepository.findById(cleaning.getItemId()).orElseThrow();
        item.setCleaning(null);
        itemRepository.save(item);
        cleaningRepository.delete(cleaning);
    }

}
