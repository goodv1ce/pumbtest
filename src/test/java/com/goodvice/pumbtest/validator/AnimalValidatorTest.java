package com.goodvice.pumbtest.validator;

import com.goodvice.pumbtest.model.Animal;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AnimalValidatorTest {
    @Test
    public void testValidateModels() {
        AnimalValidator validator = new AnimalValidator();

        List<Animal> animals = initializeAnimalArray();
        validator.validateModels(animals);

        assertEquals(5, animals.size());
        assertEquals(1, animals.get(0).getCategory().intValue());
        assertEquals(2, animals.get(1).getCategory().intValue());
        assertEquals(3, animals.get(2).getCategory().intValue());
        assertEquals(4, animals.get(3).getCategory().intValue());
        assertEquals(4, animals.get(4).getCategory().intValue());
    }

    private static List<Animal> initializeAnimalArray() {
        List<Animal> animals = new ArrayList<>();
        animals.add(animalAllArgsConstructor("test1", "", "test1", -5, null));
        animals.add(animalAllArgsConstructor("test2", "test2", "test2", 33, null));
        animals.add(animalAllArgsConstructor("test3", "test3", "test3", 21, 1));
        animals.add(animalAllArgsConstructor("test4", "test4", "test4", 8, 21));
        animals.add(animalAllArgsConstructor("test5", "", null, 15, null));
        animals.add(animalAllArgsConstructor("test6", "test6", "test6", 3, 41));
        animals.add(animalAllArgsConstructor("test7", "test7", "test7", 35, 61));
        animals.add(animalAllArgsConstructor("test7", "test7", "test7", 3, 300));

        return animals;
    }

    private static Animal animalAllArgsConstructor(String name, String type, String sex, Integer weight, Integer cost) {
        Animal animal = new Animal();
        animal.setName(name);
        animal.setType(type);
        animal.setSex(sex);
        animal.setWeight(weight);
        animal.setCost(cost);

        return animal;
    }
}
