package com.recipe.cook.controller;

import com.recipe.cook.entity.*;
import com.recipe.cook.service.MiscService;
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
@RequestMapping("/misc")
public class MiscController {

    final Logger LOGGER = LoggerFactory.getLogger(MiscController.class);

    private MiscService miscService;

    @Autowired
    public MiscController(MiscService miscService) {
        this.miscService = miscService;
    }

    @GetMapping("/quick-answer/ask")
    public String getQuickAnswerPage(Model theModel) {
        theModel.addAttribute("quickAnswerSearch", true);
        return "misc/misc-home";
    }

    @GetMapping("/quick-answer/answer")
    public String getQuickAnswerResults(@RequestParam("question") String question,
                                        Model theModel,
                                        RedirectAttributes redirectAttributes) {
        QuickAnswerResult quickAnswerResult;

        if ((quickAnswerResult = miscService.getQuickAnswer(question)) != null) {
            theModel.addAttribute("answer", quickAnswerResult);
            return "misc/misc-display";
        } else {
            redirectAttributes.addFlashAttribute("answerNotFound", "No answer could be found.");
            return "redirect:/misc/quick-answer/ask";
        }
    }

    @GetMapping("/detect-food/analyzer-page")
    public String getDetectFoodPage(Model theModel) {
        theModel.addAttribute("analyzeText", true);
        return "/misc/misc-home";
    }

    @GetMapping("/detect-food/analyze")
    public String getDetectFoodResult(@RequestParam("text") String text,
                                      Model theModel,
                                      RedirectAttributes redirectAttributes) {
        DetectedFoodList detectedFoodList;

        if ((detectedFoodList = miscService.detectFoodInText(text)) != null) {
            theModel.addAttribute("detectedFoodList", detectedFoodList);
            return "/misc/misc-display";
        } else {
            redirectAttributes.addFlashAttribute("NoDetectedFood", "No food items were detected in the text given");
            return "redirect:/misc/detect-food/analyzer-page";
        }
    }

    @GetMapping("/site-search/search")
    public String getSiteSearchPage(Model theModel) {
        theModel.addAttribute("siteSearch", true);
        return "/misc/misc-home";
    }

    @GetMapping("/site-search/results")
    public String getSiteSearchResults(@RequestParam("query") String query, Model theModel) {
        SiteContent siteContent;

        siteContent = miscService.searchSiteContent(query);
        theModel.addAttribute("siteContent", siteContent);

        return "/misc/misc-display";
    }

    @GetMapping("/food-video/search")
    public String getFoodVideoSearchPage(Model theModel) {
        theModel.addAttribute("foodVideoSearch", true);
        return "misc/misc-home";
    }

    @GetMapping("/food-video/results")
    public String getFoodVideoResults(@RequestParam("query") String query,
                                      @RequestParam("type") String type,
                                      @RequestParam("cuisine") String cuisine,
                                      @RequestParam("diet") String diet,
                                      @RequestParam("number") int number,
                                      Model theModel,
                                      RedirectAttributes redirectAttributes) {
        VideoResults videoResults;

        if ((videoResults = miscService.searchFoodVideos(query, type, cuisine, diet, number)) != null) {
            theModel.addAttribute("foodVideos", videoResults);
            return "/misc/misc-display";
        } else {
            redirectAttributes.addFlashAttribute("noDetectedFood", "No videos found with those settings");
            return "redirect:/misc/food-video/search";
        }

    }

    @GetMapping("/random/joke")
    public String getRandomJoke(Model theModel) {
        TextResponse joke = miscService.getRandomFoodJoke();
        theModel.addAttribute("joke", joke);

        return "/misc/misc-display";
    }

    @GetMapping("/random/trivia")
    public String getRandomTrivia(Model theModel) {
        TextResponse trivia = miscService.getRandomFoodTrivia();
        theModel.addAttribute("trivia", trivia);

        return "/misc/misc-display";

    }


}
