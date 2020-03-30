package com.recipe.cook.controller;

import com.recipe.cook.entity.*;
import com.recipe.cook.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping("/recipe-search/search")
    public String getRecipeSearchPage(Model theModel) {
        theModel.addAttribute("recipeSearch", true);
        return "recipe/recipe-home";
    }

    @GetMapping("/recipe-search/results")
    public String getRecipeSearchResults(@RequestParam("query") String query,
                                         @RequestParam("cuisine") String cuisine,
                                         @RequestParam("diet") String diet,
                                         @RequestParam(value = "intolerances", required = false, defaultValue = "None") List<String> intoleranceList,
                                         @RequestParam("numOfResults") int numOfResults,
                                         @RequestParam(value = "instructionsRequired", required = false, defaultValue = "false") boolean instructionsRequired,
                                         Model theModel,
                                         RedirectAttributes redirectAttributes) {

        RecipeResults recipeResults;

        String intolerances = String.join(",", intoleranceList);

        if ((recipeResults = recipeService.searchRecipes(query, cuisine, diet, intolerances, numOfResults,
                true, instructionsRequired)) != null) {
            theModel.addAttribute("recipeResults", recipeResults);
            return "recipe/recipe-display";
        } else {
            String msg = "No recipes found with those settings";
            redirectAttributes.addFlashAttribute("noRecipeFound", msg);
            return "redirect:/recipe/recipe-search/results";
        }
    }


    @GetMapping("/ingredients/recipe-search")
    public String getRecipeWithIngredientsSearchPage(Model theModel) {
        theModel.addAttribute("recipeWithIngredientsSearch", true);
        return "recipe/recipe-home";
    }

    @GetMapping("/ingredients/recipe-with-ingredients")
    public String getRecipeWithIngredients(@RequestParam("ingredients") String ingredients,
                                           @RequestParam("numOfResults") int numOfResults,
                                           @RequestParam(value = "ignorePantry", required = false, defaultValue = "false") boolean ignorePantry,
                                           Model theModel,
                                           RedirectAttributes redirectAttributes) {

        List<RecipeWithIngredients> recipesWithIngredientsList;

        if ((recipesWithIngredientsList = recipeService.searchRecipesByIngredient(ingredients, numOfResults, true,
                ignorePantry)) != null) {
            theModel.addAttribute("recipesWithIngredientsList", recipesWithIngredientsList);
            return "recipe/recipe-display";
        } else {
            String msg = "No recipe found with those settings";
            redirectAttributes.addFlashAttribute("noRecipeFound", msg);
            return "redirect:/recipe/ingredients/recipe-search";
        }
    }

    @GetMapping("/autocomplete/search")
    public String getAutoCompleteRecipeSearchPage(Model theModel) {
        theModel.addAttribute("autocompleteSearch", true);
        return "recipe/recipe-home";
    }

    @GetMapping("/autocomplete/results")
    public String getAutoCompleteRecipeSearchResults(@RequestParam("query") String query,
                                                     @RequestParam("numOfResults") int numOfResults,
                                                     Model theModel,
                                                     RedirectAttributes redirectAttributes) {

        List<AutoCompletedItem> autoCompletedItems;

        if ((autoCompletedItems = recipeService.searchRecipesAutoComplete(query, numOfResults)) != null) {
            theModel.addAttribute("autoCompletedItems", autoCompletedItems);
            return "recipe/recipe-display";
        } else {
            String msg = "No recipes found for " + query;
            redirectAttributes.addFlashAttribute("noRecipeFound", msg);
            return "redirect:/recipe/autocomplete/search";
        }
    }

    @GetMapping("/ingredients/{id}/info")
    public String getRecipeInformationResults(@PathVariable("id") int recipeId, Model theModel) {
        RecipeInformation recipeInformation = recipeService.getRecipeInformation(recipeId, true);
        theModel.addAttribute("recipeInformation", recipeInformation);
        return "recipe/recipe-display";
    }


    @GetMapping("/similar/{id}")
    public String getSimilarRecipesResults(@PathVariable("id") int recipeId,
                                           @RequestParam("numOfResults") int numOfResults,
                                           Model theModel) {
        RecipeResults recipeList = recipeService.getSimilarRecipes(recipeId, numOfResults, false);
        theModel.addAttribute("recipeResults", recipeList);
        return "recipe/recipe-display";
    }

    @GetMapping("/random/search")
    public String getRandomRecipesSearchPage(Model theModel) {
        theModel.addAttribute("randomRecipeSearch", true);
        return "recipe/recipe-home";
    }

    @GetMapping("/random/results")
    public String getRandomRecipesResults(@RequestParam("tags") String tags,
                                          @RequestParam("numOfResults") int numOfResults,
                                          Model theModel,
                                          RedirectAttributes redirectAttributes) {

        RandomRecipeResults randomRecipeResults;

        if ((randomRecipeResults = recipeService.getRandomRecipes(true, tags, numOfResults)) != null) {
            theModel.addAttribute("randomRecipeResults", randomRecipeResults);
            return "recipe/recipe-display";
        } else {
            String msg = "No recipes found with given tags";
            redirectAttributes.addFlashAttribute("noRecipeFound", msg);
            return "redirect:/recipe/random/results";
        }
    }


    @GetMapping("/summary/{id}")
    public String getRecipeSummaryResult(@PathVariable("id") int recipeId,
                                         Model theModel) {
        RecipeSummary recipeSummary = recipeService.getRecipeSummary(recipeId);
        theModel.addAttribute("recipeSummary", recipeSummary);
        return "recipe/recipe-display";
    }


}
