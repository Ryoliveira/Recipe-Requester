package com.recipe.cook.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WinePairing {

    private String foodName;

    private String pairingText;

    private List<String> pairedWines;

    private List<WineProduct> wineProductMatches;
}
