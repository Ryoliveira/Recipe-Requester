package com.recipe.cook.service;

import com.recipe.cook.entity.DishPairing;
import com.recipe.cook.entity.WineRecommendation;
import com.recipe.cook.entity.WineDescription;
import com.recipe.cook.entity.WinePairing;

public interface WineService {
    public DishPairing getDishPairing(String wineName);

    public WinePairing getWinePairing(String foodName, int maxPrice);

    public WineDescription getWineDescription(String wineName);

    public WineRecommendation getWineRecommendation(String wineName, int maxPrice, double minRating, int number);

}
