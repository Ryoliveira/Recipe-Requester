package com.recipe.cook.controller;

import com.recipe.cook.entity.DishPairing;
import com.recipe.cook.entity.WineDescription;
import com.recipe.cook.entity.WinePairing;
import com.recipe.cook.entity.WineRecommendation;
import com.recipe.cook.service.WineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/wine")
public class WineController {

    final Logger LOGGER = LoggerFactory.getLogger(WineController.class);

    private WineService wineService;

    @Autowired
    public WineController(WineService wineService) {
        this.wineService = wineService;
    }


    @GetMapping("/dishes/search")
    public String getDishPairingSearchPage(Model theModel) {
        theModel.addAttribute("dishPairingSearch", true);
        return "wine/wine-home";
    }

    @GetMapping("/dishes/results")
    public String getDishPairingResults(@RequestParam("wineName") String wineName,
                                        Model theModel, RedirectAttributes redirectAttr) {
        DishPairing dishPairing;

        if ((dishPairing = wineService.getDishPairing(wineName)) != null) {
            theModel.addAttribute("pairing", dishPairing);
            return "wine/wine-display";
        } else {
            String msg = new StringBuilder("No pairings found for ").append(wineName).toString();
            redirectAttr.addFlashAttribute("noDishPairingFound", msg);
            return "redirect:/wine/dishes/search";
        }
    }

    @GetMapping("/pairing/search")
    public String getWinePairingSearchPage(Model theModel) {
        theModel.addAttribute("winePairingSearch", true);
        return "wine/wine-home";
    }

    @GetMapping("/pairing/results")
    public String getWinePairingResults(@RequestParam("foodName") String foodName,
                                        @RequestParam("maxPrice") int maxPrice,
                                        Model theModel, RedirectAttributes redirectAttr) {
        WinePairing winePairing;

        if ((winePairing = wineService.getWinePairing(foodName, maxPrice)) != null) {
            theModel.addAttribute("winePairing", winePairing);
            return "wine/wine-display";
        } else {
            String msg = new StringBuilder("No pairings found for ").append(foodName).toString();
            redirectAttr.addFlashAttribute("noWinePairingFound", msg);
            return "redirect:/wine/pairing/search";
        }
    }

    @GetMapping("/recommendations/search")
    public String getWineRecommendationsSearchPage(Model theModel) {
        theModel.addAttribute("wineRecommendationSearch", true);
        return "wine/wine-home";
    }

    @GetMapping("/recommendations/results")
    public String getWineRecommendationsResults(@RequestParam("wineName") String wineName,
                                        @RequestParam("maxPrice") int maxPrice,
                                        @RequestParam("minRating") double minRating,
                                        @RequestParam("number") int number,
                                        Model theModel, RedirectAttributes redirectAttr) {
        WineRecommendation wineRecommendation;

        if ((wineRecommendation = wineService.getWineRecommendation(wineName, maxPrice, minRating, number)) != null) {
            theModel.addAttribute("wineRecommendation", wineRecommendation);
            return "wine/wine-display";
        } else {
            String msg = new StringBuilder(wineName).append(" not found.").toString();
            redirectAttr.addFlashAttribute("noRecommendationFound", msg);
            return "redirect:/wine/recommendations/search";
        }
    }

    @GetMapping("/description/search")
    public String getWineDescriptionSearchPage(Model theModel) {
        theModel.addAttribute("wineDescriptionSearch", true);
        return "wine/wine-home";
    }

    @GetMapping("/description/result")
    public String getWineDescriptionResult(@RequestParam("wineName") String wineName,
                                           Model theModel, RedirectAttributes redirectAttr) {
        WineDescription wineDescription;

        if ((wineDescription = wineService.getWineDescription(wineName)) != null) {
            theModel.addAttribute("wineDescription", wineDescription);
            return "wine/wine-display";
        } else {
            String msg = new StringBuilder(wineName).append(" not found.").toString();
            redirectAttr.addFlashAttribute("noDescFound", msg);
            return "redirect:/wine/description/search";
        }
    }
}
