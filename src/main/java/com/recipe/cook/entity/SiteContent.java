package com.recipe.cook.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SiteContent {

    @JsonProperty("Articles")
    private List<DataResult> articleList;

    @JsonProperty("Grocery Products")
    private List<DataResult> groceryProductList;

    @JsonProperty("Menu Items")
    private List<DataResult> menuItemList;

    @JsonProperty("Recipes")
    private List<DataResult> recipeList;

}
