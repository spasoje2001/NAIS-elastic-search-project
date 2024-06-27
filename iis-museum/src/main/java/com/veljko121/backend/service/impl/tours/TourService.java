package com.veljko121.backend.service.impl.tours;

import com.veljko121.backend.core.service.impl.CRUDService;
import com.veljko121.backend.model.tours.Tour;
import com.veljko121.backend.repository.tours.TourRepository;
import com.veljko121.backend.service.tours.ITourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourService extends CRUDService<Tour, Integer> implements ITourService {

    @Autowired
    private final TourRepository tourRepository;

    public TourService(JpaRepository<Tour, Integer> repository, TourRepository tourRepository) {
        super(repository);
        this.tourRepository = tourRepository;
    }

    @Override
    public Tour save(Tour tour) {
        return tourRepository.save(tour);
    }

    @Override
    public List<Tour> findAll(){
        return tourRepository.findAll();
    }

    public List<Tour> findByOrganizerId(Integer organizerId){
        return tourRepository.findByOrganizerId(organizerId);
    }

    @Override
    public Tour update(Tour updated) {
        return tourRepository.save(updated);
    }

    @Override
    public void delete(Tour tour){
        tourRepository.delete(tour);
    }
}
