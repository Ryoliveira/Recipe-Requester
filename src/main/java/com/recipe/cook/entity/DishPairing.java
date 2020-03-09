package com.recipe.cook.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DishPairing {

    private String wineName;

    private String text;

    private List<String> pairings;
}
