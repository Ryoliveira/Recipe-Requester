package com.recipe.cook.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DetectedFoodList {

    private List<Food> annotations;

}
