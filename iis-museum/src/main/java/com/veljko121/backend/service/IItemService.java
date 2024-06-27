package com.veljko121.backend.service;

import java.util.List;

import com.veljko121.backend.core.service.ICRUDService;
import com.veljko121.backend.model.Item;

public interface IItemService extends ICRUDService<Item, Integer>{

    List<Item> findAll();

    List<Item> getAllItemsForCleaning();

    List<Item> getAllItemsForDisplay();

    Item update(Item item);

    Item putItemIntoRoom(Integer itemId,Integer roomId);

    List<Item> findByName(String name);

    List<Item> getCleansedItemsForPreviousMonth();
    List<Item> getCleansedItemsForRestaurateur(Integer userId);

}
