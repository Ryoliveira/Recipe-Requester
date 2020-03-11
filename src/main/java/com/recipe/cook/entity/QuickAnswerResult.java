package com.recipe.cook.entity;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuickAnswerResult {

    private String question;

    private String answer;

    private String image;

    private String type;

}
