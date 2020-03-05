package com.recipe.cook.model;

import java.util.List;

public class DishPairing {

    private String wineName;

    private String text;

    private List<String> pairings;

    public DishPairing() {
    }

    public DishPairing(String text, List<String> pairings) {
        this.text = text;
        this.pairings = pairings;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getPairings() {
        return pairings;
    }

    public void setPairings(List<String> pairings) {
        this.pairings = pairings;
    }

    public String getWineName() {
        return wineName;
    }

    public void setWineName(String wineName) {
        this.wineName = wineName;
    }

    @Override
    public String toString() {
        return "DishPairing{" +
                "text='" + text + '\'' +
                ", pairings=" + pairings +
                '}';
    }
}
