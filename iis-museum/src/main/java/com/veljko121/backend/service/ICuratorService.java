package com.veljko121.backend.service;

import com.veljko121.backend.core.service.ICRUDService;
import com.veljko121.backend.model.Curator;

import java.util.List;

public interface ICuratorService extends ICRUDService<Curator, Integer> {

    Curator update(Curator updated);

    Curator findByUsername(String username);

    List<Curator> findAll();

}
