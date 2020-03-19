package com.recipe.cook.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemResultList {

    @JsonProperty("menuItems")
    private List<MenuItem> menuItemList;

    private int totalMenuItems;

    private String type;

    @JsonProperty("number")
    private int numOfResults;

}
