package com.recipe.cook.service;

import com.recipe.cook.entity.MenuItem;
import com.recipe.cook.entity.MenuItemAutoCompleteResultList;
import com.recipe.cook.entity.MenuItemResultList;

public interface MenuItemService {

    MenuItemResultList searchMenuItems(String query, int minCalories, int maxCalories, int numOfResults);

    MenuItemAutoCompleteResultList searchMenuItemsAutoComplete(String query, int numOfResults);

    MenuItem getMenuItemInfo(int itemId);

}
