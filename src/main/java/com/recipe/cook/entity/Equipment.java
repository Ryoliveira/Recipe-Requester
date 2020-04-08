package com.recipe.cook.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {

    private int id;

    private String name;

    private String image;

    private Temperature temperature;

}
