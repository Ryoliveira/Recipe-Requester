package com.recipe.cook.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WineRecommendation {

    private String wineName;

    private List<WineProduct> recommendedWines;

}
