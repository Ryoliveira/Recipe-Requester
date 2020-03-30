package com.recipe.cook.controller;

import com.recipe.cook.entity.AutoCompleteResults;
import com.recipe.cook.entity.MenuItem;
import com.recipe.cook.entity.MenuItemResults;
import com.recipe.cook.service.MenuItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/menu")
public class MenuItemController {

    final Logger LOGGER = LoggerFactory.getLogger(MenuItemController.class);

    private MenuItemService menuItemService;

    @Autowired
    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping("/menu-item/search")
    public String getSearchMenuItemPage(Model theModel) {
        theModel.addAttribute("menuItemSearch", true);
        return "menu/menu-home";
    }

    @GetMapping("/menu-item/results")
    public String getMenuItemSearchResults(@RequestParam("query") String query,
                                           @RequestParam("minCalories") int minCalories,
                                           @RequestParam("maxCalories") int maxCalories,
                                           @RequestParam("number") int numOfResults,
                                           Model theModel,
                                           RedirectAttributes redirectAttributes) {
        MenuItemResults menuItemResults;

        if ((menuItemResults = menuItemService.searchMenuItems(query, minCalories, maxCalories, numOfResults)) != null) {
            theModel.addAttribute("menuItemResults", menuItemResults);
            return "menu/menu-display";
        } else {
            String msg = "No items found for current request";
            redirectAttributes.addFlashAttribute("menuItemsNotFound", msg);
            return "redirect:/menu/menu-item/search";
        }
    }

    @GetMapping("/auto-complete/search")
    public String getAutoCompleteSearchMenuItemPage(Model theModel) {
        theModel.addAttribute("autoCompleteSearch", true);
        return "menu/menu-home";
    }

    @GetMapping("/auto-complete/results")
    public String getAutoCompleteSearchMenuItemResults(@RequestParam("query") String query,
                                                       @RequestParam("number") int numOfResults,
                                                       Model theModel,
                                                       RedirectAttributes redirectAttributes) {
        AutoCompleteResults menuItemResultList;

        if ((menuItemResultList = menuItemService.searchMenuItemsAutoComplete(query, numOfResults)) != null) {
            theModel.addAttribute("AutoCompleteResults", menuItemResultList);
            return "menu/menu-display";
        } else {
            String msg = "No menu items found";
            redirectAttributes.addFlashAttribute("noMenuItemsFound", msg);
            return "redirect:/menu/auto-complete/search";
        }
    }

    @GetMapping("/menu-item/nutrition/{id}")
    public String getMenuItemNutritionInfo(@PathVariable("id") int itemId,
                                           Model theModel) {
        MenuItem menuItem = menuItemService.getMenuItemInfo(itemId);
        theModel.addAttribute("menuItemInfo", menuItem);

        return "menu/menu-display";
    }
}
