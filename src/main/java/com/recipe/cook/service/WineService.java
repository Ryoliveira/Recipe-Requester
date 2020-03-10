package com.recipe.cook.service;

import com.recipe.cook.entity.DishPairing;
import com.recipe.cook.entity.WineDescription;
import com.recipe.cook.entity.WinePairing;
import com.recipe.cook.entity.WineRecommendation;

public interface WineService {
    DishPairing getDishPairing(String wineName);

    WinePairing getWinePairing(String foodName, int maxPrice);

    WineDescription getWineDescription(String wineName);

    WineRecommendation getWineRecommendation(String wineName, int maxPrice, double minRating, int number);

}
