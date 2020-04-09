package com.recipe.cook;


import com.recipe.cook.entity.*;
import com.recipe.cook.service.RecipeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RecipeServiceTests {

    @InjectMocks
    RecipeServiceImpl recipeServiceMock;

    @Mock
    RestTemplate restTemplateMock;

    @BeforeEach
    public void setUrl() {
        ReflectionTestUtils.setField(recipeServiceMock, "spoonacularUrl", "https://api.spoonacular.com");
    }

    @Test
    public void searchRecipes_recipeFound() {

        RecipeResults recipeResultsSpy = spy(RecipeResults.class);
        List<Recipe> recipeList = Collections.singletonList(new Recipe());
        recipeResultsSpy.setRecipeList(recipeList);

        when(restTemplateMock.getForObject(anyString(), eq(RecipeResults.class))).thenReturn(recipeResultsSpy);

        assertEquals(recipeResultsSpy, recipeServiceMock.searchRecipes("test", "test", "test",
                "test", 1, true, true));
        verify(restTemplateMock, atLeastOnce()).getForObject(anyString(), eq(RecipeResults.class));
    }

    @Test
    public void searchRecipes_recipeNotFound() {
        RecipeResults recipeResultsMock = mock(RecipeResults.class);

        when(restTemplateMock.getForObject(anyString(), eq(RecipeResults.class))).thenReturn(recipeResultsMock);

        assertNull(recipeServiceMock.searchRecipes("test", "test", "test",
                "test", 1, true, true));
        verify(restTemplateMock, atLeastOnce()).getForObject(anyString(), eq(RecipeResults.class));
    }

    @Test
    public void searchRecipesByIngredient_recipesFound() {
        List<RecipeWithIngredients> recipeWithIngredientsList = new ArrayList<>();

        when(restTemplateMock.exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(new ParameterizedTypeReference<List<RecipeWithIngredients>>() {
        }))).thenReturn(new ResponseEntity<>(recipeWithIngredientsList, HttpStatus.OK));

        assertNull(recipeServiceMock.searchRecipesByIngredient("Test", 2, true, true));
        verify(restTemplateMock, atLeastOnce()).exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(new ParameterizedTypeReference<List<RecipeWithIngredients>>() {
        }));
    }

    @Test
    public void searchRecipesAutoComplete_foundItems() {
        List<AutoCompletedItem> autoCompletedItems = Collections.singletonList(new AutoCompletedItem());

        when(restTemplateMock.exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(new ParameterizedTypeReference<List<AutoCompletedItem>>() {
        }))).thenReturn(new ResponseEntity<>(autoCompletedItems, HttpStatus.OK));

        assertEquals(autoCompletedItems, recipeServiceMock.searchRecipesAutoComplete("Test", 2));
        verify(restTemplateMock, atLeastOnce()).exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(new ParameterizedTypeReference<List<AutoCompletedItem>>() {
        }));
    }

    @Test
    public void searchRecipesAutoComplete_itemsNotFound() {
        List<AutoCompletedItem> autoCompletedItems = new ArrayList<>();

        when(restTemplateMock.exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(new ParameterizedTypeReference<List<AutoCompletedItem>>() {
        }))).thenReturn(new ResponseEntity<>(autoCompletedItems, HttpStatus.OK));

        assertNull(recipeServiceMock.searchRecipesAutoComplete("Test", 2));
        verify(restTemplateMock, atLeastOnce()).exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(new ParameterizedTypeReference<List<AutoCompletedItem>>() {
        }));
    }

    @Test
    public void getRecipeInformation_foundInformation() {
        RecipeInformation recipeInformationSpy = spy(RecipeInformation.class);
        when(restTemplateMock.getForObject(anyString(), eq(RecipeInformation.class))).thenReturn(recipeInformationSpy);

        assertEquals(recipeInformationSpy, recipeServiceMock.getRecipeInformation(1234, true));
        verify(restTemplateMock, atLeastOnce()).getForObject(anyString(), eq(RecipeInformation.class));
    }

    @Test
    public void getSimilarRecipes_foundRecipe() {
        List<Recipe> recipeList = Collections.singletonList(new Recipe());

        when(restTemplateMock.exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(new ParameterizedTypeReference<List<Recipe>>() {
        }))).thenReturn(new ResponseEntity<>(recipeList, HttpStatus.OK));

        assertNotNull(recipeServiceMock.getSimilarRecipes(1234, 2, true));
        verify(restTemplateMock, atLeastOnce()).exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(new ParameterizedTypeReference<List<Recipe>>() {
        }));
    }

    @Test
    public void getSimilarRecipes_recipeNotFound() {
        List<Recipe> recipeList = new ArrayList<>();

        when(restTemplateMock.exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(new ParameterizedTypeReference<List<Recipe>>() {
        }))).thenReturn(new ResponseEntity<>(recipeList, HttpStatus.OK));

        assertNull(recipeServiceMock.getSimilarRecipes(1234, 2, true));
        verify(restTemplateMock, atLeastOnce()).exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(new ParameterizedTypeReference<List<Recipe>>() {
        }));
    }

    @Test
    public void getRandomRecipes_foundRecipes() {
        RandomRecipeResults randomRecipeResultsSpy = spy(RandomRecipeResults.class);
        List<RecipeInformation> recipeList = Collections.singletonList(new RecipeInformation());
        randomRecipeResultsSpy.setRecipes(recipeList);

        when(restTemplateMock.getForObject(anyString(), eq(RandomRecipeResults.class))).thenReturn(randomRecipeResultsSpy);

        assertEquals(randomRecipeResultsSpy, recipeServiceMock.getRandomRecipes(true, "test", 1));
        verify(restTemplateMock, atLeastOnce()).getForObject(anyString(), eq(RandomRecipeResults.class));

    }

    @Test
    public void getRandomRecipes_recipesNotFound() {
        RandomRecipeResults randomRecipeResultsSpy = spy(RandomRecipeResults.class);
        List<RecipeInformation> recipeList = new ArrayList<>();
        randomRecipeResultsSpy.setRecipes(recipeList);

        when(restTemplateMock.getForObject(anyString(), eq(RandomRecipeResults.class))).thenReturn(randomRecipeResultsSpy);

        assertNull(recipeServiceMock.getRandomRecipes(true, "test", 1));
        verify(restTemplateMock, atLeastOnce()).getForObject(anyString(), eq(RandomRecipeResults.class));

    }

    @Test
    public void getRandomRecipes_noTags_foundRecipes() {
        RandomRecipeResults randomRecipeResultsSpy = spy(RandomRecipeResults.class);
        List<RecipeInformation> recipeList = Collections.singletonList(new RecipeInformation());
        randomRecipeResultsSpy.setRecipes(recipeList);

        when(restTemplateMock.getForObject(anyString(), eq(RandomRecipeResults.class))).thenReturn(randomRecipeResultsSpy);

        assertEquals(randomRecipeResultsSpy, recipeServiceMock.getRandomRecipes(true, 1));
        verify(restTemplateMock, atLeastOnce()).getForObject(anyString(), eq(RandomRecipeResults.class));

    }

    @Test
    public void getRandomRecipes_noTags_recipesNotFound() {
        RandomRecipeResults randomRecipeResultsSpy = spy(RandomRecipeResults.class);
        List<RecipeInformation> recipeList = new ArrayList<>();
        randomRecipeResultsSpy.setRecipes(recipeList);

        when(restTemplateMock.getForObject(anyString(), eq(RandomRecipeResults.class))).thenReturn(randomRecipeResultsSpy);

        assertNull(recipeServiceMock.getRandomRecipes(true, 1));
        verify(restTemplateMock, atLeastOnce()).getForObject(anyString(), eq(RandomRecipeResults.class));

    }



    @Test
    public void getRecipeSummary_foundSummary() {
        RecipeSummary recipeSummaryMock = mock(RecipeSummary.class);

        when(restTemplateMock.getForObject(anyString(), eq(RecipeSummary.class))).thenReturn(recipeSummaryMock);

        assertEquals(recipeSummaryMock, recipeServiceMock.getRecipeSummary(1234));
        verify(restTemplateMock, atLeastOnce()).getForObject(anyString(), eq(RecipeSummary.class));
    }


}
