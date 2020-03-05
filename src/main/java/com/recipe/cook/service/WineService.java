package com.recipe.cook.service;

import com.recipe.cook.model.DishPairing;
import com.recipe.cook.model.WinePairing;

public interface WineService {
    //todo change return type for all methods
    public DishPairing getDishPairing(String wineName);

    public WinePairing getWinePairing(String foodName, int maxPrice);

    public void getWineDescription(String wineName);

    public void getWineRecommendation(String wineName, int maxPrice, float minRating, int numberD);

}
