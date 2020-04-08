package com.recipe.cook.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RecipeInstructions {

    private String name;

    @JsonProperty("steps")
    private List<RecipeStep> recipeSteps;


}
