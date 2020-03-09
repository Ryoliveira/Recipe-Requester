package com.recipe.cook;


import com.recipe.cook.controller.MainController;
import com.recipe.cook.controller.WineController;
import com.recipe.cook.entity.DishPairing;
import com.recipe.cook.entity.WineRecommendation;
import com.recipe.cook.entity.WineDescription;
import com.recipe.cook.entity.WinePairing;
import com.recipe.cook.service.WineServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ControllerTests {

    MockMvc mainMvc;

    MockMvc wineMvc;

    @Mock
    WineServiceImpl wineService;


    @Before
    public void setUp() {
        final MainController mainController = new MainController();
        final WineController wineController = new WineController(wineService);

        mainMvc = MockMvcBuilders.standaloneSetup(mainController).build();
        wineMvc = MockMvcBuilders.standaloneSetup(wineController).build();
    }


    //MainController
    @Test
    public void homePage() throws Exception {
        mainMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    //WineController
    @Test
    public void wineHomePage() throws Exception {

        wineMvc.perform(get("/wine/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("wine/wine-home"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void getDishPairing_Found() throws Exception {
        DishPairing pairingMock = mock(DishPairing.class);
        when(wineService.getDishPairing(anyString())).thenReturn(pairingMock);

        wineMvc.perform(get("/wine/dishes")
                .param("wineName", anyString()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("pairing", pairingMock))
                .andExpect(view().name("wine/wine-display"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        verify(wineService, atMostOnce()).getDishPairing(anyString());
    }

    @Test
    public void getDishPairing_NotFound_Redirect() throws Exception {
        when(wineService.getDishPairing(anyString())).thenReturn(null);

        wineMvc.perform(get("/wine/dishes")
                .param("wineName", anyString()))
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("noDishPairingFound", "No pairings found for "))
                .andExpect(view().name("redirect:/wine/wine-home"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        verify(wineService, atMostOnce()).getDishPairing(anyString());
    }

    @Test
    public void getWinePairing_Found() throws Exception {
        WinePairing winePairing = mock(WinePairing.class);
        when(wineService.getWinePairing(anyString(), anyInt())).thenReturn(winePairing);

        wineMvc.perform(get("/wine/pairing")
                .param("foodName", anyString())
                .param("maxPrice", String.valueOf(anyInt())))
                .andExpect(status().isOk())
                .andExpect(model().attribute("winePairing", winePairing))
                .andExpect(view().name("wine/wine-display"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        verify(wineService, atMostOnce()).getWinePairing(anyString(), anyInt());

    }

    @Test
    public void getWinePairing_NotFound_Redirect() throws Exception {
        when(wineService.getWinePairing(anyString(), anyInt())).thenReturn(null);

        wineMvc.perform(get("/wine/pairing")
                .param("foodName", anyString())
                .param("maxPrice", String.valueOf(anyInt())))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("noWinePairingFound", "No pairings found for "))
                .andExpect(view().name("redirect:/wine/wine-home"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        verify(wineService, atMostOnce()).getWinePairing(anyString(), anyInt());
    }

    @Test
    public void getWineRecommentaions_Found() throws Exception {
        WineRecommendation wineRecommendation = mock(WineRecommendation.class);
        when(wineService.getWineRecommendation(anyString(), anyInt(), anyDouble(), anyInt())).thenReturn(wineRecommendation);

        wineMvc.perform(get("/wine/recommendations")
                .param("wineName", anyString())
                .param("maxPrice", String.valueOf(anyInt()))
                .param("minRating", String.valueOf(anyDouble()))
                .param("number", String.valueOf(anyInt())))
                .andExpect(status().isOk())
                .andExpect(model().attribute("wineRecommendation", wineRecommendation))
                .andExpect(view().name("wine/wine-display"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        verify(wineService, atMostOnce()).getWineRecommendation(anyString(), anyInt(), anyDouble(), anyInt());
    }

    @Test
    public void getWineRecommentaions_NotFound_Redirect() throws Exception {
        when(wineService.getWineRecommendation(anyString(), anyInt(), anyDouble(), anyInt())).thenReturn(null);

        wineMvc.perform(get("/wine/recommendations")
                .param("wineName", anyString())
                .param("maxPrice", String.valueOf(anyInt()))
                .param("minRating", String.valueOf(anyDouble()))
                .param("number", String.valueOf(anyInt())))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("noRecommendationFound", " not found."))
                .andExpect(view().name("redirect:/wine/wine-home"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        verify(wineService, atMostOnce()).getWineRecommendation(anyString(), anyInt(), anyDouble(), anyInt());
    }

    @Test
    public void getWineDescription_Found() throws Exception{
        WineDescription wineDescription = mock(WineDescription.class);

        when(wineService.getWineDescription(anyString())).thenReturn(wineDescription);

        wineMvc.perform(get("/wine/description")
                .param("wineName", anyString()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("wineDescription", wineDescription))
                .andExpect(view().name("wine/wine-display"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        verify(wineService, atMostOnce()).getWineDescription(anyString());
    }

    @Test
    public void getWineDescription_NotFound_Redirect() throws Exception{
        when(wineService.getWineDescription(anyString())).thenReturn(null);

        wineMvc.perform(get("/wine/description")
                .param("wineName", anyString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("noDescFound", " not found."))
                .andExpect(view().name("redirect:/wine/wine-home"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        verify(wineService, atMostOnce()).getWineDescription(anyString());
    }

}
