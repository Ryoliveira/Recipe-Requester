package com.recipe.cook;

import com.recipe.cook.entity.*;
import com.recipe.cook.service.MiscServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MiscServiceTest {


    @InjectMocks
    private MiscServiceImpl miscServiceMock;

    @Mock
    private RestTemplate restTemplateMock;

    private String dummyUrl = "https://api.spoonacular.com";

    @BeforeEach
    public void setUrl() {
        ReflectionTestUtils.setField(miscServiceMock, "spoonacularUrl", dummyUrl);
    }

    @Test
    public void getQuickAnswer_Success() {
        QuickAnswerResult quickAnswerResultSpy = spy(QuickAnswerResult.class);
        quickAnswerResultSpy.setAnswer("Test Answer");

        when(restTemplateMock.getForObject(anyString(), eq(QuickAnswerResult.class))).thenReturn(quickAnswerResultSpy);

        assertNotNull(miscServiceMock.getQuickAnswer("Test"));
        assertEquals("Test", miscServiceMock.getQuickAnswer("Test").getQuestion());
        assertEquals("Test Answer", miscServiceMock.getQuickAnswer("Test").getAnswer());
        verify(restTemplateMock, atLeastOnce()).getForObject(anyString(), eq(QuickAnswerResult.class));
    }

    @Test
    public void getQuickAnswer_failure() {
        QuickAnswerResult quickAnswerResultSpy = spy(QuickAnswerResult.class);

        when(restTemplateMock.getForObject(anyString(), eq(QuickAnswerResult.class))).thenReturn(quickAnswerResultSpy);

        assertNull(miscServiceMock.getQuickAnswer("Test"));
        verify(restTemplateMock, atLeastOnce()).getForObject(anyString(), eq(QuickAnswerResult.class));
    }

    @Test
    public void detectFoodInText_success() {
        DetectedFoodList detectedFoodListSpy = spy(DetectedFoodList.class);
        List<FoodItem> foodItemList = new ArrayList<>();
        foodItemList.add(new FoodItem());
        detectedFoodListSpy.setAnnotations(foodItemList);

        when(restTemplateMock.exchange(anyString(), eq(HttpMethod.POST), any(), eq(DetectedFoodList.class))).thenReturn(new ResponseEntity<>(detectedFoodListSpy, HttpStatus.OK));

        assertNotNull(miscServiceMock.detectFoodInText("test"));
        assertTrue(miscServiceMock.detectFoodInText("text").getAnnotations().size() == 1);
        verify(restTemplateMock, atLeastOnce()).exchange(anyString(), eq(HttpMethod.POST), any(), eq(DetectedFoodList.class));
    }

    @Test
    public void detectFoodInText_failure() {
        DetectedFoodList detectedFoodListSpy = spy(DetectedFoodList.class);
        List<FoodItem> foodItemList = new ArrayList<>();
        detectedFoodListSpy.setAnnotations(foodItemList);

        when(restTemplateMock.exchange(anyString(), eq(HttpMethod.POST), any(), eq(DetectedFoodList.class))).thenReturn(new ResponseEntity<>(detectedFoodListSpy, HttpStatus.OK));

        assertNull(miscServiceMock.detectFoodInText("text"));
        verify(restTemplateMock, atLeastOnce()).exchange(anyString(), eq(HttpMethod.POST), any(), eq(DetectedFoodList.class));
    }

    @Test
    public void searchSiteContent_success() {
        SiteContent siteContentSpy = spy(SiteContent.class);

        when(restTemplateMock.getForObject(anyString(), eq(SiteContent.class))).thenReturn(siteContentSpy);

        assertNotNull(miscServiceMock.searchSiteContent("test"));
        verify(restTemplateMock, atLeastOnce()).getForObject(anyString(), eq(SiteContent.class));
    }

    @Test
    public void searchFoodVideos_success() {
        VideoResults videoResultsSpy = spy(VideoResults.class);
        videoResultsSpy.setTotalResults(1);

        when(restTemplateMock.getForObject(anyString(), eq(VideoResults.class))).thenReturn(videoResultsSpy);

        assertNotNull(miscServiceMock.searchFoodVideos("test", "test", "test", "test", 1));
        verify(restTemplateMock, atLeastOnce()).getForObject(anyString(), eq(VideoResults.class));
    }

    @Test
    public void searchFoodVideos_failure() {
        VideoResults videoResultsSpy = spy(VideoResults.class);
        videoResultsSpy.setTotalResults(0);

        when(restTemplateMock.getForObject(anyString(), eq(VideoResults.class))).thenReturn(videoResultsSpy);

        assertNull(miscServiceMock.searchFoodVideos("test", "test", "test", "test", 1));
        verify(restTemplateMock, atLeastOnce()).getForObject(anyString(), eq(VideoResults.class));
    }

    @Test
    public void getRandomFoodJoke_success() {
        TextResponse textResponseSpy = spy(TextResponse.class);
        textResponseSpy.setText("I am on a seafood diet. Every time I see food, I eat it.\n");

        when(restTemplateMock.getForObject(anyString(), eq(TextResponse.class))).thenReturn(textResponseSpy);

        assertNotNull(miscServiceMock.getRandomFoodJoke());
        assertEquals("I am on a seafood diet. Every time I see food, I eat it.\n", miscServiceMock.getRandomFoodJoke().getText());
        verify(restTemplateMock, atLeastOnce()).getForObject(anyString(), eq(TextResponse.class));
    }

    @Test
    public void getRandomFoodTrivia_success() {
        TextResponse textResponseSpy = spy(TextResponse.class);
        textResponseSpy.setText("Starbucks donates 100% of its leftover food in partnership with the nonprofit Feeding America.\n");

        when(restTemplateMock.getForObject(anyString(), eq(TextResponse.class))).thenReturn(textResponseSpy);

        assertNotNull(miscServiceMock.getRandomFoodTrivia());
        assertEquals("Starbucks donates 100% of its leftover food in partnership with the nonprofit Feeding America.\n", miscServiceMock.getRandomFoodTrivia().getText());
        verify(restTemplateMock, atLeastOnce()).getForObject(anyString(), eq(TextResponse.class));
    }

    @Test
    public void encodeString_success() {
        assertEquals("this+is+a+test", miscServiceMock.encodeString("this is a test"));
        assertEquals("spaces+should+be+filled+with+plus+symbols", miscServiceMock.encodeString("spaces should be filled with plus symbols"));
    }

}
