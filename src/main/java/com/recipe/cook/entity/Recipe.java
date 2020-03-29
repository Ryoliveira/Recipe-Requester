package com.recipe.cook.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    private int id;

    private String title;

    private String image;

    private int readyInMinutes;

    private int servings;

}
