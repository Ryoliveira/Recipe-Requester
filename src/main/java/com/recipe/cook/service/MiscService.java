package com.recipe.cook.service;

import com.recipe.cook.entity.DetectedFoodList;
import com.recipe.cook.entity.QuickAnswerResult;
import com.recipe.cook.entity.SiteContent;
import com.recipe.cook.entity.VideoResults;

public interface MiscService {

    QuickAnswerResult getQuickAnswer(String question);

    DetectedFoodList detectFoodInText(String text);

    SiteContent searchSiteContent(String query);

    VideoResults searchFoodVideos(String query, String type, String cuisine, String diet);

    String getRandomFoodJoke();

    String getRandomFoodTrivia();
}
