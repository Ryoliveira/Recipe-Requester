package com.recipe.cook.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("productMatches")
    private List<WineProduct> wineProductMatches;
}
