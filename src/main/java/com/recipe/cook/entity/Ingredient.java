package com.recipe.cook.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

    private int id;

    private String name;

    private String aisle;

    private double amount;

    private String unit;

    private String unitLong;

    private String image;


}
