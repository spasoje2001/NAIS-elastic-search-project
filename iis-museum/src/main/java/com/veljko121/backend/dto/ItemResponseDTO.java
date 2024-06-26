package com.veljko121.backend.dto;

import com.veljko121.backend.core.enums.ItemCategory;
import com.veljko121.backend.model.Cleaning;
import com.veljko121.backend.model.Room;

import lombok.Data;
//DADATI ROOM UNUTAR DTO PRI RESPONSU
@Data
public class ItemResponseDTO {

    private Integer id;
    private String name;
    private String description;
    private String authorsName;
    private String yearOfCreation;
    private String period;
    private ItemCategory category;
    private String picture;
    private Cleaning cleaning;
    private Room room;
}
