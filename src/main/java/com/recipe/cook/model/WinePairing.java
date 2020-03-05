package com.recipe.cook.model;

import java.util.List;

public class WinePairing {

    private String foodName;

    private String pairingText;

    private List<String> pairedWines;

    private List<ProductMatches> productMatches;

    public WinePairing() {
    }

    public WinePairing(String foodName, String pairingText, List<String> pairedWines, List<ProductMatches> productMatches) {
        this.foodName = foodName;
        this.pairingText = pairingText;
        this.pairedWines = pairedWines;
        this.productMatches = productMatches;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getPairingText() {
        return pairingText;
    }

    public void setPairingText(String pairingText) {
        this.pairingText = pairingText;
    }

    public List<String> getPairedWines() {
        return pairedWines;
    }

    public void setPairedWines(List<String> pairedWines) {
        this.pairedWines = pairedWines;
    }

    public List<ProductMatches> getProductMatches() {
        return productMatches;
    }

    public void setProductMatches(List<ProductMatches> productMatches) {
        this.productMatches = productMatches;
    }

    @Override
    public String toString() {
        return "WinePairing{" +
                "pairingText='" + pairingText + '\'' +
                ", pairedWines=" + pairedWines +
                ", productMatches=" + productMatches +
                '}';
    }
}
