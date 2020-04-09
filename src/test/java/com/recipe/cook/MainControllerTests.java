package com.recipe.cook;


import com.recipe.cook.controller.MainController;
import com.recipe.cook.entity.RandomRecipeResults;
import com.recipe.cook.entity.RecipeInformation;
import com.recipe.cook.service.RecipeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class MainControllerTests {

    MockMvc mainMvc;

    @Mock
    RecipeServiceImpl recipeServiceMock;

    @Before
    public void setUp() {
        final MainController mainController = new MainController(recipeServiceMock);
        mainMvc = MockMvcBuilders.standaloneSetup(mainController).build();
    }


    //MainController
    @Test
    public void homePage() throws Exception {
        RecipeInformation recipeInformationMock = mock(RecipeInformation.class);
        List<RecipeInformation> recipeInformationList = new ArrayList<>();
        recipeInformationList.add(recipeInformationMock);
        RandomRecipeResults randomRecipeResults = spy(RandomRecipeResults.class);
        randomRecipeResults.setRecipes(recipeInformationList);


        when(recipeServiceMock.getRandomRecipes(true, 1)).thenReturn(randomRecipeResults);

        mainMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("recipe", recipeInformationMock))
                .andExpect(view().name("home"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


}
