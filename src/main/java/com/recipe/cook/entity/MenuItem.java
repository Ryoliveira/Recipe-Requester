package com.recipe.cook.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {

    private int id;

    private String title;

    private String restaurantChain;

    private String image;

    private List<String> images;

    @JsonProperty("nutrition")
    private NutritionInfo nutritionInfo;

}
