package com.recipe.cook.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RecipeStep {

    private int number;

    private String step;

    private List<Ingredient> ingredients;

    private List<Equipment> equipment;

    @JsonProperty("length")
    private RecipeCookTime recipeCookTime;



}
