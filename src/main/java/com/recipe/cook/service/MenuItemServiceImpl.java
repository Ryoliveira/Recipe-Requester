package com.recipe.cook.service;

import com.recipe.cook.entity.AutoCompleteResults;
import com.recipe.cook.entity.MenuItem;
import com.recipe.cook.entity.MenuItemResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class MenuItemServiceImpl implements MenuItemService {


    final Logger LOGGER = LoggerFactory.getLogger(MenuItemServiceImpl.class);

    private RestTemplate restTemplate;

    @Value("${spoonacular.url}")
    private String spoonacularUrl;

    @Value("${spoon.key}")
    private String key;

    @Autowired
    public MenuItemServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public MenuItemResults searchMenuItems(String query, int minCalories, int maxCalories, int numOfResults) {
        String encodedQuery = encodeString(query);

        String url = UriComponentsBuilder.fromHttpUrl(spoonacularUrl).path("/food/menuItems/search")
                .queryParam("apiKey", key)
                .queryParam("query", encodedQuery)
                .queryParam("minCalories", minCalories)
                .queryParam("maxCalories", maxCalories)
                .queryParam("number", numOfResults)
                .toUriString();

        MenuItemResults menuItemResults = restTemplate.getForObject(url, MenuItemResults.class);

        LOGGER.info(menuItemResults.toString());

        if (menuItemResults.getTotalMenuItems() == 0) {
            return null;
        } else {
            return menuItemResults;
        }
    }

    @Override
    public AutoCompleteResults searchMenuItemsAutoComplete(String query, int numOfResults) {
        String encodedQuery = encodeString(query);

        String url = UriComponentsBuilder.fromHttpUrl(spoonacularUrl).path("/food/menuItems/suggest")
                .queryParam("apiKey", key)
                .queryParam("query", encodedQuery)
                .queryParam("number", numOfResults)
                .toUriString();

        AutoCompleteResults menuItemResultList = restTemplate.getForObject(url, AutoCompleteResults.class);

        LOGGER.info(menuItemResultList.toString());

        if (menuItemResultList.getResults().size() == 0) {
            return null;
        } else {
            return menuItemResultList;
        }
    }

    @Override
    public MenuItem getMenuItemInfo(int itemId) {
        String url = UriComponentsBuilder.fromHttpUrl(spoonacularUrl).path("/food/menuItems/" + itemId)
                .queryParam("apiKey", key)
                .toUriString();

        MenuItem menuItem = restTemplate.getForObject(url, MenuItem.class);

        LOGGER.info(menuItem.toString());

        return menuItem;
    }

    public String encodeString(String s) {
        String encodedString = null;
        try {
            encodedString = URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedString;
    }
}
