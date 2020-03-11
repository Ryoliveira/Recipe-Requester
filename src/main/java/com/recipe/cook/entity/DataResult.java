package com.recipe.cook.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DataResult {

    @JsonProperty("dataPoints")
    private List<DataPoint> dataPointList;

    private String image;

    private String link;

    private String name;
}
