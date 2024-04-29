package com.goodvice.pumbtest.mapper;

import com.goodvice.pumbtest.model.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class AnimalCsvParserTests {
    AnimalFileParser csvParser;

    @BeforeEach
    public void setUp() {
        csvParser = new AnimalCsvParser();
    }

    @Test
    public void parse_WithValidInputs_ListOfAnimals() throws IOException {
        String content = """
                Name,Type,Sex,Weight,Cost
                Buddy,cat,female,41,78
                Cooper,,female,46,23
                Rocky,dog,,18,""";

        MultipartFile file = new MockMultipartFile("animals.csv", content.getBytes());

        List<Animal> animals = csvParser.parse(file);

        assertEquals(3, animals.size());

        Animal firstAnimal = animals.get(0);
        assertNull(firstAnimal.getId());
        assertEquals("Buddy", firstAnimal.getName());
        assertEquals("cat", firstAnimal.getType());
        assertEquals("female", firstAnimal.getSex());
        assertEquals(Integer.valueOf(41), firstAnimal.getWeight());
        assertEquals(Integer.valueOf(78), firstAnimal.getCost());
        assertNull(firstAnimal.getCategory());

        Animal secondAnimal = animals.get(1);
        assertNull(secondAnimal.getId());
        assertEquals("Cooper", secondAnimal.getName());
        assertEquals("", secondAnimal.getType());
        assertEquals("female", secondAnimal.getSex());
        assertEquals(Integer.valueOf(46), secondAnimal.getWeight());
        assertEquals(Integer.valueOf(23), secondAnimal.getCost());
        assertNull(secondAnimal.getCategory());

        Animal thirdAnimal = animals.get(2);
        assertNull(thirdAnimal.getId());
        assertEquals("Rocky", thirdAnimal.getName());
        assertEquals("dog", thirdAnimal.getType());
        assertEquals("", thirdAnimal.getSex());
        assertEquals(Integer.valueOf(18), thirdAnimal.getWeight());
        assertNull(thirdAnimal.getCost());
        assertNull(thirdAnimal.getCategory());
    }
}
