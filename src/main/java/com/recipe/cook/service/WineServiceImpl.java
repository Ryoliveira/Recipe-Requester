package com.recipe.cook.service;

import com.recipe.cook.entity.DishPairing;
import com.recipe.cook.entity.WineDescription;
import com.recipe.cook.entity.WinePairing;
import com.recipe.cook.entity.WineRecommendation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.recipe.cook.util.Encoder.encodeString;

@Service
public class WineServiceImpl implements WineService {

    final Logger LOGGER = LoggerFactory.getLogger(WineServiceImpl.class);

    private RestTemplate restTemplate;

    @Value("${spoonacular.url}")
    private String spoonacularUrl;

    @Value("${spoon.key}")
    private String key;

    @Autowired
    public WineServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public DishPairing getDishPairing(String wineName) {
        DishPairing pairingResult;
        String encodedName;
        try {
            encodedName = URLEncoder.encode(wineName, StandardCharsets.UTF_8.toString());

            String url = UriComponentsBuilder.fromHttpUrl(spoonacularUrl).path("/food/wine/dishes")
                    .queryParam("apiKey", key)
                    .queryParam("wine", encodedName)
                    .toUriString();

            pairingResult = restTemplate.getForObject(url, DishPairing.class);
            pairingResult.setWineName(StringUtils.capitalize(wineName));

            LOGGER.info(pairingResult.toString());

        } catch (HttpClientErrorException.BadRequest | UnsupportedEncodingException e) {
            return null;
        }

        return pairingResult;
    }

    @Override
    public WinePairing getWinePairing(String foodName, int maxPrice) {
        WinePairing winePairing;
        String encodedFoodName = encodeString(foodName);

        String url = UriComponentsBuilder.fromHttpUrl(spoonacularUrl).path("/food/wine/pairing")
                .queryParam("apiKey", key)
                .queryParam("food", encodedFoodName)
                .queryParam("maxPrice", maxPrice)
                .toUriString();


        winePairing = restTemplate.getForObject(url, WinePairing.class);
        winePairing.setFoodName(foodName);

        LOGGER.info(winePairing.toString());

        if (winePairing.getPairedWines() == null) {
            return null;
        } else {
            return winePairing;
        }
    }

    @Override
    public WineDescription getWineDescription(String wineName) {
        WineDescription wineDescription;
        String encodedWineName;
        try {
            encodedWineName = URLEncoder.encode(wineName, StandardCharsets.UTF_8.toString());

            String url = UriComponentsBuilder.fromHttpUrl(spoonacularUrl).path("/food/wine/description")
                    .queryParam("apiKey", key)
                    .queryParam("wine", encodedWineName)
                    .toUriString();

            wineDescription = restTemplate.getForObject(url, WineDescription.class);
            wineDescription.setWineName(StringUtils.capitalize(wineName));

            LOGGER.info(wineDescription.toString());

        } catch (HttpClientErrorException.BadRequest | UnsupportedEncodingException e) {
            return null;
        }
        return wineDescription;
    }

    @Override
    public WineRecommendation getWineRecommendation(String wineName, int maxPrice, double minRating, int number) {
        WineRecommendation wineRecommendation;
        String encodedWineName;
        try {
            encodedWineName = URLEncoder.encode(wineName, StandardCharsets.UTF_8.toString());

            String url = UriComponentsBuilder.fromHttpUrl(spoonacularUrl).path("/food/wine/recommendation")
                    .queryParam("apiKey", key)
                    .queryParam("wine", encodedWineName)
                    .queryParam("maxPrice", maxPrice)
                    .queryParam("minRating", minRating)
                    .queryParam("number", number)
                    .toUriString();

            wineRecommendation = restTemplate.getForObject(url, WineRecommendation.class);
            wineRecommendation.setWineName(StringUtils.capitalize(wineName));

            LOGGER.info(wineRecommendation.toString());

        } catch (HttpClientErrorException.BadRequest | UnsupportedEncodingException e) {
            return null;
        }
        return wineRecommendation;
    }
}
