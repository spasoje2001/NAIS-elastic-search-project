package com.veljko121.backend.service.impl.tours;

import com.veljko121.backend.core.service.impl.CRUDService;
import com.veljko121.backend.model.tours.TourPricelist;
import com.veljko121.backend.repository.tours.TourPricelistRepository;
import com.veljko121.backend.service.tours.ITourPricelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TourPricelistService extends CRUDService<TourPricelist, Integer> implements ITourPricelistService {

    @Autowired
    private final TourPricelistRepository tourPricelistRepository;

    public TourPricelistService(JpaRepository<TourPricelist, Integer> repository, TourPricelistRepository tourPricelistRepository) {
        super(repository);
        this.tourPricelistRepository = tourPricelistRepository;
    }

    @Override
    public TourPricelist save(TourPricelist pricelist) {
        return tourPricelistRepository.save(pricelist);
    }

    @Override
    public TourPricelist update(TourPricelist updated) {
        return tourPricelistRepository.save(updated);
    }

    @Override
    public TourPricelist findById(Integer id) { return tourPricelistRepository.findById(id).orElse(null); }
}
