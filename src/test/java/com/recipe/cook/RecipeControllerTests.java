package com.recipe.cook;

import com.recipe.cook.controller.RecipeController;
import com.recipe.cook.entity.*;
import com.recipe.cook.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class RecipeControllerTests {


    MockMvc recipeControllerMock;

    @Mock
    RecipeService recipeServiceMock;


    @Before
    public void setUp() {
        RecipeController recipeController = new RecipeController(recipeServiceMock);
        recipeControllerMock = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    public void getRecipeSearchPage_success() throws Exception {
        recipeControllerMock.perform(get("/recipe/recipe-search/search"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("recipeSearch", true))
                .andExpect(view().name("recipe/recipe-home"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getRecipeSearchResults_success() throws Exception {
        RecipeResults recipeResultsMock = mock(RecipeResults.class);
        when(recipeServiceMock.searchRecipes(anyString(), anyString(), anyString(), anyString(), anyInt(), anyBoolean(), anyBoolean())).thenReturn(recipeResultsMock);

        recipeControllerMock.perform(get("/recipe/recipe-search/results")
                .param("query", "Test")
                .param("cuisine", "Test")
                .param("diet", "Test")
                .param("intolerances", String.valueOf(Arrays.asList("test")))
                .param("numOfResults", String.valueOf(1))
                .param("instructionsRequired", String.valueOf(true)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("recipeResults", recipeResultsMock))
                .andExpect(view().name("recipe/recipe-display"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getRecipeSearchResults_redirect() throws Exception {
        when(recipeServiceMock.searchRecipes(anyString(), anyString(), anyString(), anyString(), anyInt(), anyBoolean(), anyBoolean())).thenReturn(null);

        recipeControllerMock.perform(get("/recipe/recipe-search/results")
                .param("query", "test")
                .param("cuisine", "test")
                .param("diet", "test")
                .param("intolerances", String.valueOf(Arrays.asList("test")))
                .param("numOfResults", String.valueOf(1))
                .param("instructionsRequired", String.valueOf(true)))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("noRecipeFound", "No recipes found with those settings"))
                .andExpect(view().name("redirect:/recipe/recipe-search/results"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getRecipeWithIngredientsSearchPage_success() throws Exception {
        recipeControllerMock.perform(get("/recipe/ingredients/recipe-search"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("recipeWithIngredientsSearch", true))
                .andExpect(view().name("recipe/recipe-home"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getRecipeWithIngredients_success() throws Exception {
        RecipeWithIngredients recipeWithIngredientsMock = mock(RecipeWithIngredients.class);
        List<RecipeWithIngredients> recipeWithIngredientsList = new ArrayList<>();
        recipeWithIngredientsList.add(recipeWithIngredientsMock);
        when(recipeServiceMock.searchRecipesByIngredient(anyString(), anyInt(), anyBoolean(), anyBoolean())).thenReturn(recipeWithIngredientsList);

        recipeControllerMock.perform(get("/recipe/ingredients/recipe-with-ingredients")
                .param("ingredients", "test")
                .param("numOfResults", String.valueOf(1))
                .param("ignorePantry", String.valueOf(true)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("recipesWithIngredientsList", recipeWithIngredientsList))
                .andExpect(view().name("recipe/recipe-display"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void getRecipeWithIngredients_redirect() throws Exception {
        when(recipeServiceMock.searchRecipesByIngredient(anyString(), anyInt(), anyBoolean(), anyBoolean())).thenReturn(null);

        recipeControllerMock.perform(get("/recipe/ingredients/recipe-with-ingredients")
                .param("ingredients", "test")
                .param("numOfResults", String.valueOf(1))
                .param("ignorePantry", String.valueOf(true)))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("noRecipeFound", "No recipe found with those settings"))
                .andExpect(view().name("redirect:/recipe/ingredients/recipe-search"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getAutoCompleteRecipeSearchPage_success() throws Exception {
        recipeControllerMock.perform(get("/recipe/autocomplete/search"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("autocompleteSearch", true))
                .andExpect(view().name("recipe/recipe-home"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    @Test
    public void getAutoCompleteRecipeSearchResults_success() throws Exception {
        AutoCompletedItem autoCompletedItemMock = mock(AutoCompletedItem.class);
        List<AutoCompletedItem> autoCompletedItemList = new ArrayList<>();
        autoCompletedItemList.add(autoCompletedItemMock);
        when(recipeServiceMock.searchRecipesAutoComplete(anyString(), anyInt())).thenReturn(autoCompletedItemList);

        recipeControllerMock.perform(get("/recipe/autocomplete/results")
                .param("query", "test")
                .param("numOfResults", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("autoCompletedItems", autoCompletedItemList))
                .andExpect(view().name("recipe/recipe-display"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getAutoCompleteRecipeSearchResults_redirect() throws Exception {
        when(recipeServiceMock.searchRecipesAutoComplete(anyString(), anyInt())).thenReturn(null);

        recipeControllerMock.perform(get("/recipe/autocomplete/results")
                .param("query", "pizza")
                .param("numOfResults", String.valueOf(1)))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("noRecipeFound", "No recipes found for pizza"))
                .andExpect(view().name("redirect:/recipe/autocomplete/search"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }


    @Test
    public void getRecipeInformationResults_success() throws Exception {
        RecipeInformation recipeInformationMock = mock(RecipeInformation.class);
        when(recipeServiceMock.getRecipeInformation(anyInt(), anyBoolean())).thenReturn(recipeInformationMock);

        recipeControllerMock.perform(get("/recipe/ingredients/{id}/info", 1234))
                .andExpect(status().isOk())
                .andExpect(model().attribute("recipeInformation", recipeInformationMock))
                .andExpect(view().name("recipe/recipe-display"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getSimilarRecipesResults_success() throws Exception {
        RecipeResults recipeResultsMock = mock(RecipeResults.class);
        when(recipeServiceMock.getSimilarRecipes(anyInt(), anyInt(), anyBoolean())).thenReturn(recipeResultsMock);

        recipeControllerMock.perform(get("/recipe/similar/{id}", 1234)
                .param("numOfResults", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("recipeResults", recipeResultsMock))
                .andExpect(view().name("recipe/recipe-display"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void getRandomRecipesSearchPage_success() throws Exception {
        recipeControllerMock.perform(get("/recipe/random/search"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("randomRecipeSearch", true))
                .andExpect(view().name("recipe/recipe-home"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getRandomRecipesResults_success() throws Exception {
        RandomRecipeResults randomRecipeResultsMock = mock(RandomRecipeResults.class);
        when(recipeServiceMock.getRandomRecipes(anyBoolean(), anyString(), anyInt())).thenReturn(randomRecipeResultsMock);

        recipeControllerMock.perform(get("/recipe/random/results")
                .param("tags", "test")
                .param("numOfResults", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("randomRecipeResults", randomRecipeResultsMock))
                .andExpect(view().name("recipe/recipe-display"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getRandomRecipesResults_redirect() throws Exception {
        when(recipeServiceMock.getRandomRecipes(anyBoolean(), anyString(), anyInt())).thenReturn(null);

        recipeControllerMock.perform(get("/recipe/random/results")
                .param("tags", "test")
                .param("numOfResults", String.valueOf(1)))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("noRecipeFound", "No recipes found with given tags"))
                .andExpect(view().name("redirect:/recipe/random/results"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getRecipeSummaryResult_success() throws Exception {
        RecipeSummary recipeSummaryMock = mock(RecipeSummary.class);
        when(recipeServiceMock.getRecipeSummary(anyInt())).thenReturn(recipeSummaryMock);

        recipeControllerMock.perform(get("/recipe/summary/{id}", 1234))
                .andExpect(status().isOk())
                .andExpect(model().attribute("recipeSummary", recipeSummaryMock))
                .andExpect(view().name("recipe/recipe-display"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getRecipeInstructions_success() throws Exception {
        List<RecipeInstructions> recipeInstructions = new ArrayList<>();
        RecipeInformation recipeInformationMock = mock(RecipeInformation.class);

        when(recipeServiceMock.getRecipeInstructions(anyInt())).thenReturn(recipeInstructions);
        when(recipeServiceMock.getRecipeInformation(anyInt(), anyBoolean())).thenReturn(recipeInformationMock);

        recipeControllerMock.perform(get("/recipe/{id}/recipe-instructions", 1234)
                            .param("recipeName","test"))
                            .andExpect(status().isOk())
                            .andExpect(model().attribute("recipeInstructions", recipeInstructions))
                            .andExpect(model().attribute("recipeInformation", recipeInformationMock))
                            .andExpect(model().attribute("recipeName", "test"))
                            .andExpect(view().name("recipe/recipe-instructions"))
                            .andDo(MockMvcResultHandlers.print())
                            .andReturn();
    }
}
