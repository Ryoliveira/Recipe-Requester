package com.recipe.cook.entity;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Nutrients {

    private String title;

    private double amount;

    private String unit;

    private double percentOfDailyNeeds;

}
