package com.recipe.cook.entity;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WineProduct {

    private int id;

    private String title;

    private String description;

    private String price;

    private String imageUrl;

    private double averageRating;

    private double ratingCount;

    private double score;

    private String link;
}
