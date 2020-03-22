package com.recipe.cook;

import com.recipe.cook.entity.DishPairing;
import com.recipe.cook.entity.WineDescription;
import com.recipe.cook.entity.WinePairing;
import com.recipe.cook.entity.WineRecommendation;
import com.recipe.cook.service.WineServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    private WineServiceImpl wineServiceMock;

    @Mock
    private RestTemplate restTemplateMock;


    @BeforeEach
    public void setUrl() {
        ReflectionTestUtils.setField(wineServiceMock, "spoonacularUrl", "https://api.spoonacular.com");
    }

    @Test
    public void getDishPairing_Found() {
        DishPairing dishPairing = new DishPairing();

        when(restTemplateMock.getForObject(anyString(), eq(DishPairing.class))).thenReturn(dishPairing);

        assertEquals(dishPairing, wineServiceMock.getDishPairing("wineName"));
        verify(restTemplateMock, atMostOnce()).getForObject(anyString(), eq(DishPairing.class));
    }

    @Test
    public void getDishPairing_NotFound() {
        when(restTemplateMock.getForObject(anyString(), eq(DishPairing.class))).thenThrow(HttpClientErrorException.BadRequest.class);

        assertNull(wineServiceMock.getDishPairing("wineName"));
        verify(restTemplateMock, atMostOnce()).getForObject(anyString(), eq(DishPairing.class));

    }

    @Test
    public void getWinePairing_Found() {
        List<String> dummyWines = Arrays.asList("merlot", "riesling");
        WinePairing winePairing = mock(WinePairing.class);
        winePairing.setPairedWines(dummyWines);
        when(restTemplateMock.getForObject(anyString(), eq(WinePairing.class))).thenReturn(winePairing);

        assertEquals(winePairing, wineServiceMock.getWinePairing(anyString(), anyInt()));
        verify(restTemplateMock, atMostOnce()).getForObject(anyString(), eq(WinePairing.class));

    }

    @Test
    public void getWinePairing_NotFound() {
        WinePairing winePairing = new WinePairing();
        when(restTemplateMock.getForObject(anyString(), eq(WinePairing.class))).thenReturn(winePairing);

        assertNull(wineServiceMock.getWinePairing(anyString(), anyInt()));
        verify(restTemplateMock, atMostOnce()).getForObject(anyString(), eq(WinePairing.class));
    }

    @Test
    public void getWineDescription_Found() {
        WineDescription wineDescription = mock(WineDescription.class);
        when(restTemplateMock.getForObject(anyString(), eq(WineDescription.class))).thenReturn(wineDescription);

        assertEquals(wineDescription, wineServiceMock.getWineDescription("wineName"));
        verify(restTemplateMock, atMostOnce()).getForObject(anyString(), eq(WineDescription.class));
    }

    @Test
    public void getWineDescription_NotFound() {
        when(restTemplateMock.getForObject(anyString(), eq(WineDescription.class))).thenThrow(HttpClientErrorException.BadRequest.class);

        assertNull(wineServiceMock.getWineDescription("wineName"));
        verify(restTemplateMock, atMostOnce()).getForObject(anyString(), eq(WineDescription.class));
    }

    @Test
    public void getWineRecommendation_Found() {
        WineRecommendation wineRecommendation = new WineRecommendation();

        when(restTemplateMock.getForObject(anyString(), eq(WineRecommendation.class))).thenReturn(wineRecommendation);

        assertEquals(wineRecommendation, wineServiceMock.getWineRecommendation("wineName", 1, 1.0, 1));
        verify(restTemplateMock, atMostOnce()).getForObject(anyString(), eq(WineRecommendation.class));
    }

    @Test
    public void getWineRecommendation_NotFound() {
        when(restTemplateMock.getForObject(anyString(), eq(WineRecommendation.class))).thenThrow(HttpClientErrorException.BadRequest.class);

        assertNull(wineServiceMock.getWineRecommendation("wineName", 1, 1.0, 1));
        verify(restTemplateMock, atMostOnce()).getForObject(anyString(), eq(WineRecommendation.class));
    }
}
