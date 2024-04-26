package com.goodvice.pumbtest.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class AnimalTests {
    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void testNoArgsConstructor() {
        Animal animal = new Animal();

        assertNull(animal.getName());
        assertNull(animal.getType());
        assertNull(animal.getSex());
        assertNull(animal.getCost());
        assertNull(animal.getWeight());
    }

    @Test
    public void testAnimalValidationNotNull() {
        String nullMessage = "must not be null";

        Animal animal = new Animal();

        assertTrue(checkIfFieldAndMessageExpected(validator, "name", nullMessage, animal));
        assertTrue(checkIfFieldAndMessageExpected(validator, "type", nullMessage, animal));
        assertTrue(checkIfFieldAndMessageExpected(validator, "sex", nullMessage, animal));
        assertTrue(checkIfFieldAndMessageExpected(validator, "cost", nullMessage, animal));
        assertTrue(checkIfFieldAndMessageExpected(validator, "weight", nullMessage, animal));
    }

    @Test
    public void testAnimalValidationNotEmpty() {
        String emptyMessage = "must not be empty";

        Animal animal = new Animal();
        animal.setName("");
        animal.setType("");
        animal.setSex("");

        assertTrue(checkIfFieldAndMessageExpected(validator, "name", emptyMessage, animal));
        assertTrue(checkIfFieldAndMessageExpected(validator, "type", emptyMessage, animal));
        assertTrue(checkIfFieldAndMessageExpected(validator, "sex", emptyMessage, animal));
    }

    @Test
    public void testAnimalValidationMinBound() {
        String greaterEqualMessage = "must be greater than or equal to 1";
        String lessEqualMessage = "must be less than or equal to 4";

        Animal animal = new Animal();
        animal.setWeight(0);
        animal.setCost(0);
        animal.setCategory(0);

        assertTrue(checkIfFieldAndMessageExpected(validator, "weight", greaterEqualMessage, animal));
        assertTrue(checkIfFieldAndMessageExpected(validator, "cost", greaterEqualMessage, animal));
        assertTrue(checkIfFieldAndMessageExpected(validator, "category", greaterEqualMessage, animal));

        animal.setCategory(5);
        assertTrue(checkIfFieldAndMessageExpected(validator, "category", lessEqualMessage, animal));
    }

    public static boolean checkIfFieldAndMessageExpected(
            Validator validator,
            String fieldName,
            String message,
            Animal animal
    ) {
        var violations = validator.validate(animal);
        return violations.stream().anyMatch(violation ->
                violation.getMessage().equals(message) &&
                violation.getPropertyPath().toString().equals(fieldName));
    }
}
