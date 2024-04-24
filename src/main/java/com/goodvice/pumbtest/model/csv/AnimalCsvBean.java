package com.goodvice.pumbtest.model.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;

@Getter
public class AnimalCsvBean extends CsvBean {
    @CsvBindByName(column = "Name", required = true)
    private String name;

    @CsvBindByName(column = "Type", required = true)
    private String type;

    @CsvBindByName(column = "Sex", required = true)
    private String sex;

    @CsvBindByName(column = "Weight", required = true)
    private int weight;

    @CsvBindByName(column = "Cost", required = true)
    private int cost;
}
