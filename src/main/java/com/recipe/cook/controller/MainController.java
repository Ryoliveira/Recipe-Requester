package com.recipe.cook.controller;

import com.recipe.cook.entity.Recipe;
import com.recipe.cook.entity.RecipeInformation;
import com.recipe.cook.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    RecipeService recipeService;

    @Autowired
    public MainController(RecipeService recipeService){
        this.recipeService = recipeService;
    }

    @GetMapping("/")
    public String showHomePage(Model theModel) {

        Recipe recipe = recipeService.getRandomRecipes(true, 1).getRecipes().get(0);
        theModel.addAttribute("recipe", recipe);
        return "home";
    }


}
