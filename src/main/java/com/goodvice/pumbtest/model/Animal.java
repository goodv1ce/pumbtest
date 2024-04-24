package com.goodvice.pumbtest.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Animal {
    
    private String name;
    private String type;
    private String sex;
    private int weight;
    private int cost;
}
