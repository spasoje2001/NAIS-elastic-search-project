package com.veljko121.backend.service.tours;

import com.veljko121.backend.core.service.ICRUDService;
import com.veljko121.backend.model.tours.TourPricelist;

public interface ITourPricelistService extends ICRUDService<TourPricelist, Integer> {

    TourPricelist save(TourPricelist pricelist);

    TourPricelist findById(Integer id);

    TourPricelist update(TourPricelist updated);

}
