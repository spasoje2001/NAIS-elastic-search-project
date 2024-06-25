package com.veljko121.backend.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.veljko121.backend.dto.ItemCreateDTO;
import com.veljko121.backend.dto.ItemResponseDTO;
import com.veljko121.backend.dto.ItemUpdateDTO;
import com.veljko121.backend.model.Item;
import com.veljko121.backend.service.IItemService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ModelMapper modelMapper;
    private final IItemService itemService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ItemCreateDTO itemDTO) {
        var item = modelMapper.map(itemDTO, Item.class);
        itemService.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemDTO);
    }
    
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Item> items = itemService.findAll();
        var itemResponse = items.stream()
                .map(tour -> modelMapper.map(tour, Item.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED).body(itemResponse);
    }

    @GetMapping("/forCleaning")
    public List<Item> getAllItemsForCleaning() {
        return itemService.getAllItemsForCleaning();
    }

    @GetMapping("/{itemId}")
    public Item getItem(@PathVariable Integer itemId) {
        return itemService.findById(itemId);
    }

    @GetMapping("/forDisplay")
    public ResponseEntity<?> getAllItemsForDisplay() {
        List<Item> items = itemService.getAllItemsForDisplay();
        var itemResponse = items.stream()
                .map(tour -> modelMapper.map(tour, Item.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED).body(itemResponse);
    }

    @PutMapping
    public ResponseEntity<?> updateItem(@RequestBody ItemUpdateDTO itemDTO){
        var item =itemService.findById(itemDTO.getId());
        item.setAuthorsName(itemDTO.getAuthorsName());
        item.setCategory(itemDTO.getCategory());
        item.setDescription(itemDTO.getDescription());
        item.setName(itemDTO.getName());
        item.setPeriod(itemDTO.getPeriod());
        item.setPicture(itemDTO.getPicture());
        item.setYearOfCreation(itemDTO.getYearOfCreation());
        itemService.update(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemDTO);
    }

    @PutMapping("/putIntoRoom/{itemId}/{roomId}")
    public Item putItemIntoRoom(@PathVariable Integer itemId, @PathVariable Integer roomId){
        return itemService.putItemIntoRoom(itemId, roomId);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        List<Item> items = itemService.findByName(name);
        var itemResponse = items.stream()
                .map(tour -> modelMapper.map(tour, Item.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(itemResponse);
    }

}
