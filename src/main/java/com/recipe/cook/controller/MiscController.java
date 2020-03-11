package com.recipe.cook.controller;

import com.recipe.cook.entity.QuickAnswerResult;
import com.recipe.cook.service.MiscService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/misc")
public class MiscController {

    final Logger LOGGER = LoggerFactory.getLogger(MiscController.class);

    private MiscService miscService;

    @Autowired
    public MiscController(MiscService miscService){
        this.miscService = miscService;
    }

    @GetMapping("/quick-answer/ask")
    public String getQuickAnswerPage(Model theModel){
        theModel.addAttribute("quickAnswerSearch", true);
        return "misc/misc-home";
    }

    @GetMapping("/quick-answer/answer")
    public String getQuickAnswerResults(@RequestParam("question") String question, Model theModel){
        QuickAnswerResult quickAnswerResult;

        quickAnswerResult = miscService.getQuickAnswer(question);
        theModel.addAttribute("answer", quickAnswerResult);

        LOGGER.info(quickAnswerResult.toString());


        return "misc/misc-display";
    }


}
