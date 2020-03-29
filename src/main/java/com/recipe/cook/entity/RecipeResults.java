package com.recipe.cook.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RecipeResults {

    @JsonProperty("results")
    private List<Recipe> recipeList;

    private int totalResults;
}
