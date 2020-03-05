package com.recipe.cook.controller;


import com.recipe.cook.model.DishPairing;
import com.recipe.cook.model.WinePairing;
import com.recipe.cook.service.WineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/wine")
public class WineController {

    final Logger LOGGER = LoggerFactory.getLogger(WineController.class);

    private WineService wineService;

    @Autowired
    public WineController(WineService wineService) {
        this.wineService = wineService;
    }


    @GetMapping("/home")
    public String wineHomePage() {
        return "wine/home";
    }

    @GetMapping("/dishes")
    public String getDishPairing(@RequestParam("wineName") String wineName, Model theModel) {
        DishPairing dishPairing;

        if ((dishPairing = wineService.getDishPairing(wineName)).getPairings() != null) {
            theModel.addAttribute("pairing", dishPairing);
        }

        return "wine/display-dish-pairing";
    }

    @GetMapping("/pairing")
    public String getWinePairing(@RequestParam("food") String foodName, @RequestParam("maxPrice") int maxPrice, Model theModel) {
        WinePairing winePairing;

        if ((winePairing = wineService.getWinePairing(foodName, maxPrice)).getProductMatches() != null) {
            theModel.addAttribute("winePairing", winePairing);
        }

        LOGGER.info(winePairing.toString());

        return "wine/display-wine-pairing";
    }


}
