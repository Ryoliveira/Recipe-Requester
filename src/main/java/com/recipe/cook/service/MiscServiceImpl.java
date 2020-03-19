package com.recipe.cook.service;

import com.recipe.cook.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class MiscServiceImpl implements MiscService {

    final Logger LOGGER = LoggerFactory.getLogger(MiscServiceImpl.class);

    private RestTemplate restTemplate;

    @Value("${spoonacular.url}")
    private String spoonacularUrl;

    @Value("${spoon.key}")
    private String key;

    @Autowired
    public MiscServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public QuickAnswerResult getQuickAnswer(String question) {
        QuickAnswerResult quickAnswerResult;

        String encodedQuestion = encodeString(question);
        String url = UriComponentsBuilder.fromHttpUrl(spoonacularUrl).path("/recipes/quickAnswer")
                .queryParam("apiKey", key)
                .queryParam("q", encodedQuestion)
                .toUriString();

        quickAnswerResult = restTemplate.getForObject(url, QuickAnswerResult.class);
        if (quickAnswerResult.getAnswer() != null) {
            quickAnswerResult.setQuestion(question);
            LOGGER.info(quickAnswerResult.toString());
            return quickAnswerResult;
        } else {
            return null;
        }
    }

    @Override
    public DetectedFoodList detectFoodInText(String text) {
        DetectedFoodList detectedFoodList;

        String encodedText = encodeString(text);
        String url = UriComponentsBuilder.fromHttpUrl(spoonacularUrl).path("/food/detect")
                .queryParam("apiKey", key)
                .queryParam("text", encodedText)
                .toUriString();
        //Setting media type
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        HttpEntity<String> entity = new HttpEntity<>(headers);


        ResponseEntity<DetectedFoodList> response = restTemplate.exchange(url, HttpMethod.POST, entity, DetectedFoodList.class);
        detectedFoodList = response.getBody();

        if (!detectedFoodList.getAnnotations().isEmpty()) {
            LOGGER.info(detectedFoodList.toString());
            return detectedFoodList;
        } else {
            return null;
        }
    }

    @Override
    public SiteContent searchSiteContent(String query) {
        SiteContent siteContent;
        String encodedQuery = encodeString(query);

        String url = UriComponentsBuilder.fromHttpUrl(spoonacularUrl).path("/food/site/search")
                .queryParam("apiKey", key)
                .queryParam("query", encodedQuery)
                .toUriString();

        siteContent = restTemplate.getForObject(url, SiteContent.class);

        LOGGER.info(siteContent.toString());

        return siteContent;
    }

    @Override
    public VideoResults searchFoodVideos(String query, String type, String cuisine, String diet, int number) {
        VideoResults videoResults;

        String encodedQuery = encodeString(query);
        String encodedType = encodeString(type);
        String encodedCuisine = encodeString(cuisine);
        String encodedDiet = encodeString(diet);

        String url = UriComponentsBuilder.fromHttpUrl(spoonacularUrl).path("food/videos/search")
                .queryParam("apiKey", key)
                .queryParam("query", encodedQuery)
                .queryParam("type", encodedType)
                .queryParam("cuisine", encodedCuisine)
                .queryParam("diet", encodedDiet)
                .queryParam("number", number)
                .toUriString();

        videoResults = restTemplate.getForObject(url, VideoResults.class);
        if (videoResults.getTotalResults() != 0) {
            LOGGER.info(videoResults.toString());
            return videoResults;
        } else {
            return null;
        }
    }

    @Override
    public TextResponse getRandomFoodJoke() {
        String url = UriComponentsBuilder.fromHttpUrl(spoonacularUrl).path("food/jokes/random")
                .queryParam("apiKey", key)
                .toUriString();

        TextResponse joke = restTemplate.getForObject(url, TextResponse.class);

        LOGGER.info(joke.toString());

        return joke;
    }

    @Override
    public TextResponse getRandomFoodTrivia() {
        String url = UriComponentsBuilder.fromHttpUrl(spoonacularUrl).path("food/trivia/random")
                .queryParam("apiKey", key)
                .toUriString();

        TextResponse trivia = restTemplate.getForObject(url, TextResponse.class);

        LOGGER.info(trivia.toString());

        return trivia;
    }

    public String encodeString(String s) {
        String encodedString = null;
        try {
            encodedString = URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedString;
    }
}
