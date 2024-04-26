package com.goodvice.pumbtest.mapper;

import com.goodvice.pumbtest.model.Animal;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AnimalXmlParserTests {
    @Test
    public void testParse() throws IOException {
        MockMultipartFile file = getMockMultipartFile();
        AnimalXmlParser xmlParser = new AnimalXmlParser();

        List<Animal> animals = xmlParser.parse(file);

        assertEquals(3, animals.size());

        // check first animal
        Animal firstAnimal = animals.get(0);
        assertNull(firstAnimal.getId());
        assertEquals("Tucker", firstAnimal.getName());
        assertEquals("cat", firstAnimal.getType());
        assertEquals("female", firstAnimal.getSex());
        assertEquals(Integer.valueOf(10), firstAnimal.getWeight());
        assertEquals(Integer.valueOf(44), firstAnimal.getCost());
        assertNull(firstAnimal.getCategory());

        // check second animal
        Animal secondAnimal = animals.get(1);
        assertNull(secondAnimal.getId());
        assertEquals("Jack", secondAnimal.getName());
        assertEquals("cat", secondAnimal.getType());
        assertEquals("female", secondAnimal.getSex());
        assertNull(secondAnimal.getWeight());
        assertEquals(Integer.valueOf(12), secondAnimal.getCost());
        assertNull(secondAnimal.getCategory());

        // check third animal
        Animal thirdAnimal = animals.get(2);
        assertNull(thirdAnimal.getId());
        assertEquals("Zoe", thirdAnimal.getName());
        assertEquals("cat", thirdAnimal.getType());
        assertEquals("male", thirdAnimal.getSex());
        assertEquals(Integer.valueOf(30), thirdAnimal.getWeight());
        assertEquals(Integer.valueOf(49), thirdAnimal.getCost());
        assertNull(thirdAnimal.getCategory());
    }

    private static MockMultipartFile getMockMultipartFile() {
        String content = """
                <animals>
                	<animal>
                		<name>Tucker</name>
                		<type>cat</type>
                		<sex>female</sex>
                		<weight>10</weight>
                		<cost>44</cost>
                	</animal>
                	<animal>
                		<name>Jack</name>
                		<type>cat</type>
                		<sex>female</sex>
                		<cost>12</cost>
                	</animal>
                	<animal>
                		<name>Zoe</name>
                		<type>cat</type>
                		<sex>male</sex>
                		<weight>30</weight>
                		<cost>49</cost>
                	</animal>
                </animals>
                """;

        return new MockMultipartFile("animals.xml", content.getBytes());
    }
}
