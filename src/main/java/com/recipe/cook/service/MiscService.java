package com.recipe.cook.service;

import com.recipe.cook.entity.*;

public interface MiscService {

    QuickAnswerResult getQuickAnswer(String question);

    DetectedFoodList detectFoodInText(String text);

    SiteContent searchSiteContent(String query);

    VideoResults searchFoodVideos(String query, String type, String cuisine, String diet, int number);

    TextResponse getRandomFoodJoke();

    TextResponse getRandomFoodTrivia();
}
