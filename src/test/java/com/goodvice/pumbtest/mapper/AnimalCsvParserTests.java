package com.goodvice.pumbtest.mapper;

import com.goodvice.pumbtest.model.Animal;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;


public class AnimalCsvParserTests {
    @Test
    public void testParse() throws IOException {
        String content = """
                Name,Type,Sex,Weight,Cost
                Buddy,cat,female,41,78
                Cooper,,female,46,23
                Rocky,dog,,18,""";

        MockMultipartFile file = new MockMultipartFile("animals.csv", content.getBytes());
        AnimalCsvParser csvParser = new AnimalCsvParser();

        List<Animal> animals = csvParser.parse(file);

        assertEquals(3, animals.size());

        // check first animal
        Animal firstAnimal = animals.get(0);
        assertNull(firstAnimal.getId());
        assertEquals("Buddy", firstAnimal.getName());
        assertEquals("cat", firstAnimal.getType());
        assertEquals("female", firstAnimal.getSex());
        assertEquals(Integer.valueOf(41), firstAnimal.getWeight());
        assertEquals(Integer.valueOf(78), firstAnimal.getCost());
        assertNull(firstAnimal.getCategory());

        // check second animal
        Animal secondAnimal = animals.get(1);
        assertNull(secondAnimal.getId());
        assertEquals("Cooper", secondAnimal.getName());
        assertEquals("", secondAnimal.getType());
        assertEquals("female", secondAnimal.getSex());
        assertEquals(Integer.valueOf(46), secondAnimal.getWeight());
        assertEquals(Integer.valueOf(23), secondAnimal.getCost());
        assertNull(secondAnimal.getCategory());

        // check third animal
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
