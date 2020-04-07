package com.recipe.cook.service;

import com.recipe.cook.entity.*;

import java.util.List;

public interface RecipeService {

    RecipeResults searchRecipes(String query, String cuisine, String diet, String intolerances,
                                int numOfResults, boolean limitLicense, boolean instructionsRequired);

    List<RecipeWithIngredients> searchRecipesByIngredient(String ingredients, int numOfResults, boolean limitLicense, boolean ignorePantry);

    List<AutoCompletedItem> searchRecipesAutoComplete(String query, int numOfResults);

    RecipeInformation getRecipeInformation(int recipeId, boolean includeNutrition);

    RecipeResults getSimilarRecipes(int recipeId, int numOfResults, boolean limitLicense);

    RandomRecipeResults getRandomRecipes(boolean limitLicense, String tags, int numOfResults);

    RandomRecipeResults getRandomRecipes(boolean limitLicense, int numOfResults);

    RecipeSummary getRecipeSummary(int recipeId);

}
