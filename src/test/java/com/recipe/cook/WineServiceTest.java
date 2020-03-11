package com.recipe.cook;

import com.recipe.cook.entity.DishPairing;
import com.recipe.cook.entity.WineDescription;
import com.recipe.cook.entity.WinePairing;
import com.recipe.cook.entity.WineRecommendation;
import com.recipe.cook.service.WineServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WineServiceTest {

    @InjectMocks
    private WineServiceImpl wineService;

    @Mock
    private RestTemplate restTemplate;

    private String dummyUrl = "https://api.spoonacular.com";

    @Test
    public void getDishPairing_Found() {
        DishPairing dishPairing = new DishPairing();
        ReflectionTestUtils.setField(wineService, "spoonacularUrl", dummyUrl);

        when(restTemplate.getForEntity(anyString(), eq(DishPairing.class))).thenReturn(new ResponseEntity<>(dishPairing, HttpStatus.OK));

        assertEquals(dishPairing, wineService.getDishPairing("wineName"));
        verify(restTemplate, atMostOnce()).getForEntity(anyString(), eq(DishPairing.class));
    }

    @Test
    public void getDishPairing_NotFound() {
        ReflectionTestUtils.setField(wineService, "spoonacularUrl", dummyUrl);
        when(restTemplate.getForEntity(anyString(), eq(DishPairing.class))).thenThrow(HttpClientErrorException.BadRequest.class);

        assertNull(wineService.getDishPairing("wineName"));
        verify(restTemplate, atMostOnce()).getForEntity(anyString(), eq(DishPairing.class));

    }

    @Test
    public void getWinePairing_Found() {
        List<String> dummyWines = Arrays.asList("merlot", "riesling");
        WinePairing winePairing = mock(WinePairing.class);
        winePairing.setPairedWines(dummyWines);
        ReflectionTestUtils.setField(wineService, "spoonacularUrl", dummyUrl);
        when(restTemplate.getForEntity(anyString(), eq(WinePairing.class))).thenReturn(new ResponseEntity<>(winePairing, HttpStatus.OK));

        assertEquals(winePairing, wineService.getWinePairing(anyString(), anyInt()));
        verify(restTemplate, atMostOnce()).getForEntity(anyString(), eq(WinePairing.class));

    }

    @Test
    public void getWinePairing_NotFound() {
        WinePairing winePairing = new WinePairing();
        ReflectionTestUtils.setField(wineService, "spoonacularUrl", dummyUrl);
        when(restTemplate.getForEntity(anyString(), eq(WinePairing.class))).thenReturn(new ResponseEntity<>(winePairing, HttpStatus.OK));

        assertNull(wineService.getWinePairing(anyString(), anyInt()));
        verify(restTemplate, atMostOnce()).getForEntity(anyString(), eq(WinePairing.class));

    }

    @Test
    public void getWineDescription_Found() {
        WineDescription wineDescription = mock(WineDescription.class);
        ReflectionTestUtils.setField(wineService, "spoonacularUrl", dummyUrl);
        when(restTemplate.getForEntity(anyString(), eq(WineDescription.class))).thenReturn(new ResponseEntity<>(wineDescription, HttpStatus.OK));

        assertEquals(wineDescription, wineService.getWineDescription("wineName"));
        verify(restTemplate, atMostOnce()).getForEntity(anyString(), eq(WineDescription.class));

    }

    @Test
    public void getWineDescription_NotFound() {
        ReflectionTestUtils.setField(wineService, "spoonacularUrl", dummyUrl);
        when(restTemplate.getForEntity(anyString(), eq(WineDescription.class))).thenThrow(HttpClientErrorException.BadRequest.class);

        assertNull(wineService.getWineDescription("wineName"));
        verify(restTemplate, atMostOnce()).getForEntity(anyString(), eq(WineDescription.class));
    }

    @Test
    public void getWineRecommendation_Found() {
        WineRecommendation wineRecommendation = new WineRecommendation();
        ReflectionTestUtils.setField(wineService, "spoonacularUrl", dummyUrl);
        when(restTemplate.getForEntity(anyString(), eq(WineRecommendation.class))).thenReturn(new ResponseEntity<>(wineRecommendation, HttpStatus.OK));

        assertEquals(wineRecommendation, wineService.getWineRecommendation("wineName", 1, 1.0, 1));
        verify(restTemplate, atMostOnce()).getForEntity(anyString(), eq(WineRecommendation.class));

    }

    @Test
    public void getWineRecommendation_NotFound() {
        ReflectionTestUtils.setField(wineService, "spoonacularUrl", dummyUrl);
        when(restTemplate.getForEntity(anyString(), eq(WineRecommendation.class))).thenThrow(HttpClientErrorException.BadRequest.class);

        assertNull(wineService.getWineRecommendation("wineName", 1, 1.0, 1));
        verify(restTemplate, atMostOnce()).getForEntity(anyString(), eq(WineRecommendation.class));
    }


}
