package com.recipe.cook.service;

import com.recipe.cook.entity.MenuItem;
import com.recipe.cook.entity.AutoMenuItemResultList;
import com.recipe.cook.entity.MenuItemResultList;

public interface MenuItemService {

    MenuItemResultList searchMenuItems(String query, int minCalories, int maxCalories, int numOfResults);

    AutoMenuItemResultList searchMenuItemsAutoComplete(String query, int numOfResults);

    MenuItem getMenuItemInfo(int itemId);

}
