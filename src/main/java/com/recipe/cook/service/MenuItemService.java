package com.recipe.cook.service;

import com.recipe.cook.entity.AutoCompleteResults;
import com.recipe.cook.entity.MenuItem;
import com.recipe.cook.entity.MenuItemResults;

public interface MenuItemService {

    MenuItemResults searchMenuItems(String query, int minCalories, int maxCalories, int numOfResults);

    AutoCompleteResults searchMenuItemsAutoComplete(String query, int numOfResults);

    MenuItem getMenuItemInfo(int itemId);

}
