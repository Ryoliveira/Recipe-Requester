package com.recipe.cook;


import com.recipe.cook.controller.MainController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class MainControllerTests {

    MockMvc mainMvc;

    @Before
    public void setUp() {
        final MainController mainController = new MainController();
        mainMvc = MockMvcBuilders.standaloneSetup(mainController).build();
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



}
