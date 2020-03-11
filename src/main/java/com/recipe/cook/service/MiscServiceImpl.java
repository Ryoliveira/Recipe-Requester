package com.recipe.cook.service;

import com.recipe.cook.entity.DetectedFoodList;
import com.recipe.cook.entity.QuickAnswerResult;
import com.recipe.cook.entity.SiteContent;
import com.recipe.cook.entity.VideoResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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
    public MiscServiceImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }


    @Override
    public QuickAnswerResult getQuickAnswer(String question) {
        String encodedQuestion = null;
        QuickAnswerResult quickAnswerResult;

        //Todo deal with case when question cant be answered

        try{
            encodedQuestion = URLEncoder.encode(question, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = UriComponentsBuilder.fromHttpUrl(spoonacularUrl).path("/recipes/quickAnswer")
                                         .queryParam("apiKey", key)
                                         .queryParam("q", encodedQuestion)
                                         .toUriString();

        ResponseEntity<QuickAnswerResult> response = restTemplate.getForEntity(url, QuickAnswerResult.class);

        quickAnswerResult = response.getBody();
        quickAnswerResult.setQuestion(question);

        LOGGER.info(quickAnswerResult.toString());

        return quickAnswerResult;
    }

    @Override
    public DetectedFoodList detectFoodInText(String text) {

        return null;
    }

    @Override
    public SiteContent searchSiteContent(String query) {

        return null;
    }

    @Override
    public VideoResults searchFoodVideos(String query, String type, String cuisine, String diet) {

        return null;
    }

    @Override
    public String getRandomFoodJoke() {

        return null;
    }

    @Override
    public String getRandomFoodTrivia() {

        return null;
    }
}
