package com.recipe.cook.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VideoResults {

    @JsonProperty("videos")
    private List<VideoDetails> videoDetailsList;

    private int totalResults;

}
