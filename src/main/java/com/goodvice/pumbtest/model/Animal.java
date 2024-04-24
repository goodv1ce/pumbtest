package com.goodvice.pumbtest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("Name")
    @JacksonXmlProperty(localName = "name")
    @NotNull
    private String name;

    @JsonProperty("Type")
    @JacksonXmlProperty(localName = "type")
    @NotNull
    private String type;

    @JsonProperty("Sex")
    @JacksonXmlProperty(localName = "sex")
    @NotNull
    private String sex;

    @JsonProperty("Weight")
    @JacksonXmlProperty(localName = "weight")
    @NotNull
    @Min(1)
    private int weight;

    @JsonProperty("Cost")
    @JacksonXmlProperty(localName = "cost")
    @NotNull
    @Min(1)
    private int cost;
}
