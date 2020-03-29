package com.recipe.cook.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RecipeInformation extends Recipe {

    private List<Ingredient> extendedIngredients;

    private String summary;

    private WinePairing winePairing;
}
