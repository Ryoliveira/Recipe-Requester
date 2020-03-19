package com.recipe.cook;

import com.recipe.cook.controller.MiscController;
import com.recipe.cook.entity.*;
import com.recipe.cook.service.MiscServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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
        QuickAnswerResult quickAnswerResultMock = mock(QuickAnswerResult.class);
        when(miscServiceMock.getQuickAnswer(anyString())).thenReturn(quickAnswerResultMock);

        mockMvc.perform(get("/misc/quick-answer/answer")
                .param("question", "Test"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("answer", quickAnswerResultMock))
                .andExpect(view().name("misc/misc-display"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getQuickAnswerResults_redirect() throws Exception {
        when(miscServiceMock.getQuickAnswer(anyString())).thenReturn(null);

        mockMvc.perform(get("/misc/quick-answer/answer")
                .param("question", "Test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("answerNotFound", "No answer could be found."))
                .andExpect(view().name("redirect:/misc/quick-answer/ask"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getDetectFoodPage_success() throws Exception {
        mockMvc.perform(get("/misc/detect-food/analyzer-page"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("analyzeText", true))
                .andExpect(view().name("misc/misc-home"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getDetectFoodResult_success() throws Exception {
        DetectedFoodList detectedFoodListMock = mock(DetectedFoodList.class);
        when(miscServiceMock.detectFoodInText(anyString())).thenReturn(detectedFoodListMock);

        mockMvc.perform(get("/misc/detect-food/analyze")
                .param("text", "Test"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("detectedFoodList", detectedFoodListMock))
                .andExpect(view().name("misc/misc-display"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getDetectFoodResult_redirect() throws Exception {
        when(miscServiceMock.detectFoodInText(anyString())).thenReturn(null);

        mockMvc.perform(get("/misc/detect-food/analyze")
                .param("text", "Test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("NoDetectedFood", "No food items were detected in the text given"))
                .andExpect(view().name("redirect:/misc/detect-food/analyzer-page"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getSiteSearchPage_success() throws Exception {
        mockMvc.perform(get("/misc/site-search/search"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("siteSearch", true))
                .andExpect(view().name("misc/misc-home"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getSiteSearchResults_success() throws Exception {
        SiteContent siteContentMock = mock(SiteContent.class);

        when(miscServiceMock.searchSiteContent(anyString())).thenReturn(siteContentMock);

        mockMvc.perform(get("/misc/site-search/results")
                .param("query", "Test"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("siteContent", siteContentMock))
                .andExpect(view().name("misc/misc-display"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    @Test
    public void getFoodVideoSearchPage_success() throws Exception {
        mockMvc.perform(get("/misc/food-video/search"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("foodVideoSearch", true))
                .andExpect(view().name("misc/misc-home"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getFoodVideoResults_success() throws Exception {
        VideoResults videoResults = mock(VideoResults.class);
        when(miscServiceMock.searchFoodVideos(anyString(), anyString(), anyString(), anyString(), anyInt())).thenReturn(videoResults);

        mockMvc.perform(get("/misc/food-video/results")
                .param("query", "Test")
                .param("type", "Test")
                .param("cuisine", "Test")
                .param("diet", "Test")
                .param("number", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("foodVideos", videoResults))
                .andExpect(view().name("misc/misc-display"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getFoodVideoResults_redirect() throws Exception {
        when(miscServiceMock.searchFoodVideos(anyString(), anyString(), anyString(), anyString(), anyInt())).thenReturn(null);

        mockMvc.perform(get("/misc/food-video/results")
                .param("query", "Test")
                .param("type", "Test")
                .param("cuisine", "Test")
                .param("diet", "Test")
                .param("number", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("noDetectedFood", "No videos found with those settings"))
                .andExpect(view().name("redirect:/misc/food-video/search"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getRandomJoke_success() throws Exception {
        TextResponse textResponseMock = mock(TextResponse.class);
        when(miscServiceMock.getRandomFoodJoke()).thenReturn(textResponseMock);

        mockMvc.perform(get("/misc/random/joke"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("joke", textResponseMock))
                .andExpect(view().name("misc/misc-display"));
    }

    @Test
    public void getRandomTrivia_success() throws Exception {
        TextResponse textResponseMock = mock(TextResponse.class);
        when(miscServiceMock.getRandomFoodTrivia()).thenReturn(textResponseMock);

        mockMvc.perform(get("/misc/random/trivia"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("trivia", textResponseMock))
                .andExpect(view().name("misc/misc-display"));
    }


}
