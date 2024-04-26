package com.goodvice.pumbtest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents an animal entity.
 *
 * @author goodvice
 */
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
    @NotEmpty
    private String name;

    @JsonProperty("Type")
    @JacksonXmlProperty(localName = "type")
    @NotNull
    @NotEmpty
    private String type;

    @JsonProperty("Sex")
    @JacksonXmlProperty(localName = "sex")
    @NotNull
    @NotEmpty
    private String sex;

    @JsonProperty("Weight")
    @JacksonXmlProperty(localName = "weight")
    @NotNull
    @Min(1)
    private Integer weight;

    @JsonProperty("Cost")
    @JacksonXmlProperty(localName = "cost")
    @NotNull
    @Min(1)
    private Integer cost;

    /**
     * The category of the animal.
     * Do not parse from the file, the API sets the value
     */
    @NotNull
    @Min(1)
    @Max(4)
    private Integer category;
}
