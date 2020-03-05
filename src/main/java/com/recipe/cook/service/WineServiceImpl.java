package com.recipe.cook.service;

import com.recipe.cook.model.DishPairing;
import com.recipe.cook.model.WinePairing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class WineServiceImpl implements WineService {

    final Logger LOGGER = LoggerFactory.getLogger(WineServiceImpl.class);

    @Autowired
    RestTemplate restTemplate;

    @Value("${WineDish.url}")
    String dishPairUrl;

    @Value("${WinePair.url}")
    String winePairUrl;

    @Value("${spoon.key}")
    String key;

    @Override
    public DishPairing getDishPairing(String wineName) {
        //todo handle when a wine name is not recognized
        DishPairing pairingResult = new DishPairing();
        String encodedName = "";
        try {
            encodedName = URLEncoder
                    .encode(wineName, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = UriComponentsBuilder.fromHttpUrl(dishPairUrl)
                .queryParam("apiKey", key)
                .queryParam("wine", encodedName).toUriString();

        ResponseEntity<DishPairing> result = restTemplate.getForEntity(url, DishPairing.class);

        if (result.getStatusCodeValue() == 200) {
            pairingResult = result.getBody();
            pairingResult.setWineName(wineName);
        }
        return pairingResult;
    }

    @Override
    public WinePairing getWinePairing(String foodName, int maxPrice) {
        WinePairing winePairing = new WinePairing();
        String encodedFoodName = "";

        try {
            encodedFoodName = URLEncoder.encode(foodName, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = UriComponentsBuilder.fromHttpUrl(winePairUrl)
                .queryParam("apiKey", key)
                .queryParam("food", encodedFoodName)
                .queryParam("maxPrice", maxPrice).toUriString();


        ResponseEntity<WinePairing> result = restTemplate.getForEntity(url, WinePairing.class);


        if (result.getStatusCodeValue() == 200) {
            winePairing = result.getBody();
            winePairing.setFoodName(foodName);
        }

        return winePairing;
    }

    @Override
    public void getWineDescription(String wineName) {

    }

    @Override
    public void getWineRecommendation(String wineName, int maxPrice, float minRating, int numberD) {

    }
}
