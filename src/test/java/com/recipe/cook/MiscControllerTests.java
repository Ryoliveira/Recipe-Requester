package com.recipe.cook;

import com.recipe.cook.controller.MiscController;
import com.recipe.cook.service.MiscServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class MiscControllerTests {

    MockMvc mockMvc;

    @Mock
    MiscServiceImpl miscServiceMock;

    @Before
    public void setUp() {
        MiscController miscController = new MiscController(miscServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(miscController).build();
    }

    @Test
    public void getQuickAnswerPage_success() throws Exception {
        mockMvc.perform(get("/misc/quick-answer/ask"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("quickAnswerSearch", true))
                .andExpect(view().name("misc/misc-home"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getQuickAnswerResults_success() throws Exception {

    }

    @Test
    public void getQuickAnswerResults_redirect() throws Exception {

    }

    @Test
    public void getDetectFoodPage_success() throws Exception {

    }

    @Test
    public void getDetectFoodResult_success() throws Exception {

    }

    @Test
    public void getDetectFoodResult_redirect() throws Exception {

    }

    @Test
    public void getSiteSearchPage_success() throws Exception {

    }

    @Test
    public void getSiteSearchResults_success() throws Exception {

    }

    @Test
    public void getSiteSearchResults_redirect() throws Exception {

    }

    @Test
    public void getFoodVideoSearchPage_success() throws Exception {

    }

    @Test
    public void getFoodVideoResults_success() throws Exception {

    }

    @Test
    public void getFoodVideoResults_redirect() throws Exception {

    }

    @Test
    public void getRandomJoke_success() throws Exception {

    }

    @Test
    public void getRandomTrivia_success() throws Exception {

    }


}
