package com.recipe.cook.service;

import com.recipe.cook.entity.DishPairing;
import com.recipe.cook.entity.WineDescription;
import com.recipe.cook.entity.WinePairing;
import com.recipe.cook.entity.WineRecommendation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class WineServiceImpl implements WineService {

    final Logger LOGGER = LoggerFactory.getLogger(WineServiceImpl.class);

    private RestTemplate restTemplate;

    @Value("${Wine.url}")
    private String wineUrl;

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
        ResponseEntity<DishPairing> response;
        try {
            encodedName = URLEncoder.encode(wineName, StandardCharsets.UTF_8.toString());

            String url = UriComponentsBuilder.fromHttpUrl(wineUrl).path("/dishes")
                    .queryParam("apiKey", key)
                    .queryParam("wine", encodedName).toUriString();

            response = restTemplate.getForEntity(url, DishPairing.class);
            pairingResult = response.getBody();
            pairingResult.setWineName(wineName);

            LOGGER.info(pairingResult.toString());

        } catch (HttpClientErrorException.BadRequest | UnsupportedEncodingException e) {
            return null;
        }

        return pairingResult;
    }

    @Override
    public WinePairing getWinePairing(String foodName, int maxPrice) {
        WinePairing winePairing;
        String encodedFoodName = null;

        try {
            encodedFoodName = URLEncoder.encode(foodName, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = UriComponentsBuilder.fromHttpUrl(wineUrl).path("/pairing")
                .queryParam("apiKey", key)
                .queryParam("food", encodedFoodName)
                .queryParam("maxPrice", maxPrice).toUriString();


        ResponseEntity<WinePairing> response = restTemplate.getForEntity(url, WinePairing.class);
        winePairing = response.getBody();
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
        ResponseEntity<WineDescription> response;
        try {
            encodedWineName = URLEncoder.encode(wineName, StandardCharsets.UTF_8.toString());

            String url = UriComponentsBuilder.fromHttpUrl(wineUrl).path("/description")
                    .queryParam("apiKey", key)
                    .queryParam("wine", encodedWineName).toUriString();

            response = restTemplate.getForEntity(url, WineDescription.class);
            wineDescription = response.getBody();
            wineDescription.setWineName(wineName);

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
        ResponseEntity<WineRecommendation> response;

        try {
            encodedWineName = URLEncoder.encode(wineName, StandardCharsets.UTF_8.toString());

            String url = UriComponentsBuilder.fromHttpUrl(wineUrl).path("/recommendation")
                    .queryParam("apiKey", key)
                    .queryParam("wine", encodedWineName)
                    .queryParam("maxPrice", maxPrice)
                    .queryParam("minRating", minRating)
                    .queryParam("number", number).toUriString();

            response = restTemplate.getForEntity(url, WineRecommendation.class);
            wineRecommendation = response.getBody();
            wineRecommendation.setWineName(wineName);

            LOGGER.info(wineRecommendation.toString());

        } catch (HttpClientErrorException.BadRequest | UnsupportedEncodingException e) {
            return null;
        }
        return wineRecommendation;
    }
}
