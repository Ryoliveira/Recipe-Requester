package com.recipe.cook.service;

import com.recipe.cook.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.recipe.cook.util.Encoder.encodeString;

@Service
public class RecipeServiceImpl implements RecipeService {

    final Logger LOGGER = LoggerFactory.getLogger(RecipeServiceImpl.class);

    RestTemplate restTemplate;

    @Value("${spoonacular.url}")
    private String spoonacularUrl;

    @Value("${spoon.key}")
    private String key;

    @Autowired
    public RecipeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public RecipeResults searchRecipes(String query, String cuisine, String diet, String intolerances,
                                       int numOfResults, boolean limitLicense, boolean instructionsRequired) {

        String encodedQuery = encodeString(query);
        String encodedCuisine = encodeString(cuisine);
        String encodedDiet = encodeString(diet);
        String encodedIntolerances = encodeString(intolerances);

        String url = UriComponentsBuilder.fromHttpUrl(spoonacularUrl).path("/recipes/search")
                .queryParam("apiKey", key)
                .queryParam("query", encodedQuery)
                .queryParam("cuisine", encodedCuisine)
                .queryParam("diet", encodedDiet)
                .queryParam("intolerances", encodedIntolerances)
                .queryParam("number", numOfResults)
                .queryParam("limitLicense", limitLicense)
                .toUriString();

        RecipeResults recipeResults = restTemplate.getForObject(url, RecipeResults.class);

        LOGGER.info(recipeResults.toString());

        if (!recipeResults.getRecipeList().isEmpty()) {
            return recipeResults;
        } else {
            return null;
        }
    }

    @Override
    public List<RecipeWithIngredients> searchRecipesByIngredient(String ingredients, int numOfResults, boolean limitLicense, boolean ignorePantry) {
        String encodedIngredients = encodeString(ingredients);

        String url = UriComponentsBuilder.fromHttpUrl(spoonacularUrl).path("/recipes/findByIngredients")
                .queryParam("apiKey", key)
                .queryParam("ingredients", ingredients)
                .queryParam("number", numOfResults)
                .queryParam("limitLicense", limitLicense)
                .queryParam("ignorePantry", ignorePantry)
                .toUriString();

        ResponseEntity<List<RecipeWithIngredients>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<RecipeWithIngredients>>() {
        });

        List<RecipeWithIngredients> recipeWithIngredients = response.getBody();

        LOGGER.info(recipeWithIngredients.toString());

        if(recipeWithIngredients.size() == 0){
            return null;
        }else{
            return recipeWithIngredients;
        }
    }

    @Override
    public List<AutoCompletedItem> searchRecipesAutoComplete(String query, int numOfResults) {
        String encodedQuery = encodeString(query);

        String url = UriComponentsBuilder.fromHttpUrl(spoonacularUrl).path("/recipes/autocomplete")
                .queryParam("apiKey", key)
                .queryParam("query", encodedQuery)
                .queryParam("number", numOfResults)
                .toUriString();

        ResponseEntity<List<AutoCompletedItem>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<AutoCompletedItem>>() {
        });

        List<AutoCompletedItem> autoCompletedItems = response.getBody();

        LOGGER.info(autoCompletedItems.toString());

        if (!autoCompletedItems.isEmpty()) {
            return autoCompletedItems;
        } else {
            return null;
        }
    }

    @Override
    public RecipeInformation getRecipeInformation(int recipeId, boolean includeNutrition) {

        String url = UriComponentsBuilder.fromHttpUrl(spoonacularUrl).path("/recipes/" + recipeId + "/information")
                .queryParam("apiKey", key)
                .queryParam("id", recipeId)
                .queryParam("includeNutrition", includeNutrition)
                .toUriString();

        RecipeInformation recipeInformation = restTemplate.getForObject(url, RecipeInformation.class);

        LOGGER.info(recipeInformation.toString());

        return recipeInformation;
    }

    @Override
    public RecipeResults getSimilarRecipes(int recipeId, int numOfResults, boolean limitLicense) {

        String url = UriComponentsBuilder.fromHttpUrl(spoonacularUrl).path("/recipes/" + recipeId + "/similar")
                .queryParam("apiKey", key)
                .queryParam("number", numOfResults)
                .queryParam("limitLicense", limitLicense)
                .toUriString();

        ResponseEntity<List<Recipe>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Recipe>>() {
        });

        List<Recipe> recipeList = response.getBody();

        RecipeResults recipeResults = new RecipeResults();
        recipeResults.setRecipeList(recipeList);

        LOGGER.info(recipeList.toString());

        if (recipeList.size() != 0) {
            return recipeResults;
        } else {
            return null;
        }
    }

    @Override
    public RandomRecipeResults getRandomRecipes(boolean limitLicense, String tags, int numOfResults) {
        String encodedTags = encodeString(tags);

        String url = UriComponentsBuilder.fromHttpUrl(spoonacularUrl).path("/recipes/random")
                .queryParam("apiKey", key)
                .queryParam("number", numOfResults)
                .queryParam("tags", encodedTags)
                .queryParam("limitLicense", limitLicense)
                .toUriString();


        RandomRecipeResults randomRecipeResults = restTemplate.getForObject(url, RandomRecipeResults.class);

        LOGGER.info(randomRecipeResults.toString());

        if (!randomRecipeResults.getRecipes().isEmpty()) {
            return randomRecipeResults;
        } else {
            return null;
        }
    }

    @Override
    public RandomRecipeResults getRandomRecipes(boolean limitLicense, int numOfResults) {
        String url = UriComponentsBuilder.fromHttpUrl(spoonacularUrl).path("/recipes/random")
                .queryParam("apiKey", key)
                .queryParam("number", numOfResults)
                .queryParam("limitLicense", limitLicense)
                .toUriString();


        RandomRecipeResults randomRecipeResults = restTemplate.getForObject(url, RandomRecipeResults.class);

        LOGGER.info(randomRecipeResults.toString());

        if (!randomRecipeResults.getRecipes().isEmpty()) {
            return randomRecipeResults;
        } else {
            return null;
        }
    }

    @Override
    public RecipeSummary getRecipeSummary(int recipeId) {

        String url = UriComponentsBuilder.fromHttpUrl(spoonacularUrl).path("/recipes/" + recipeId + "/summary")
                .queryParam("apiKey", key)
                .toUriString();

        RecipeSummary recipeSummary = restTemplate.getForObject(url, RecipeSummary.class);

        LOGGER.info(recipeSummary.toString());

        return recipeSummary;
    }
}
