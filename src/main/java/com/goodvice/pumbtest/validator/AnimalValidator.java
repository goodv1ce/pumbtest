package com.goodvice.pumbtest.validator;

import com.goodvice.pumbtest.model.Animal;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Validates {@link Animal} objects based on constraints defined in the model.
 */
@Component
public class AnimalValidator {

    /**
     * Validates a list of {@link Animal} objects.
     *
     * @param arrayToValidate The list of Animal objects to validate.
     */
    public void validateModels(List<Animal> arrayToValidate) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        List<Animal> invalidObjects = new ArrayList<>();
        for (Animal animal : arrayToValidate) {
            categorizeByPrice(animal);
            Set<ConstraintViolation<Animal>> violations = validator.validate(animal);
            if (!violations.isEmpty()) {
                invalidObjects.add(animal);
            }
        }
        arrayToValidate.removeAll(invalidObjects);
    }

    /**
     * Sets the category field value of the animal based on its cost.
     *
     * @param animal The Animal object to set category value.
     */
    private void categorizeByPrice(Animal animal) {
        if (animal.getCost() == null) {
            return;
        }
        double animalCost = animal.getCost();
        int animalCategory = (int) Math.ceil(animalCost / 20);
        animal.setCategory(Math.min(animalCategory, 4));
    }
}
