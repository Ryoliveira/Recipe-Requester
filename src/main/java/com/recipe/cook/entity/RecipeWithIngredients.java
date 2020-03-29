package com.recipe.cook.entity;


import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RecipeWithIngredients extends Recipe {

    private List<Ingredient> usedIngredients;

    private List<Ingredient> missedIngredients;
}
