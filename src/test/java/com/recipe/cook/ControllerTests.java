package com.recipe.cook;


import com.recipe.cook.controller.MainController;
import com.recipe.cook.controller.WineController;
import com.recipe.cook.service.WineServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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


    @Test
    public void homePage() throws Exception {
        mainMvc.perform(get("/"))
                .andExpect(view().name("home.html"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void wineHomePage() throws Exception {

        wineMvc.perform(get("/wine/home"))
                .andExpect(view().name("wine/home.html"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

}
