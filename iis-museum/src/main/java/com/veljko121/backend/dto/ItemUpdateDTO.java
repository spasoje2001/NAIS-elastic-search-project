package com.veljko121.backend.dto;

import com.veljko121.backend.core.enums.ItemCategory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemUpdateDTO {

    private Integer id;
    private String name;
    private String description;
    private String authorsName;
    private String yearOfCreation;
    private String period;
    private ItemCategory category;
    private String picture;
}
