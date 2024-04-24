package com.goodvice.pumbtest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Animals {
    @JsonProperty("animal")
    private List<Animal> animals;
}
