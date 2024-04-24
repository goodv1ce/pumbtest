package com.goodvice.pumbtest.model;

import com.opencsv.bean.CsvBindByName;
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
