package com.recipe.cook.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NutritionInfo {

    @JsonProperty("nutrients")
    private List<Nutrients> nutrientsList;

    private CaloricBreakdown caloricBreakdown;

    private double calories;

    private String carbs;

    private String fat;

    private String protein;

}
