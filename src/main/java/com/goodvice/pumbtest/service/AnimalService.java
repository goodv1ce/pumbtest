package com.goodvice.pumbtest.service;

import com.goodvice.pumbtest.mapper.AnimalCsvParser;
import com.goodvice.pumbtest.mapper.AnimalFileParser;
import com.goodvice.pumbtest.mapper.AnimalXmlParser;
import com.goodvice.pumbtest.model.Animal;
import com.goodvice.pumbtest.repository.AnimalRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;

    public void addFromFile(MultipartFile file) {
        if (file == null) {
            //todo handle null file
            return;
        }

        String contentType = file.getContentType();

        if (contentType == null) {
            //todo handle null contentType
            return;
        }

        AnimalFileParser fileMapper;
        switch (contentType) {
            case "text/csv":
                fileMapper = new AnimalCsvParser();
                break;
            case "application/xml":
                fileMapper = new AnimalXmlParser();
                break;
            default:
                //todo handle unknown format send error unsupported
                return;
        }
        List<Animal> animalList;
        try {
            animalList = fileMapper.parse(file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        List<Animal> validatedAnimals = new ArrayList<>();

        for (Animal animal : animalList) {
            Set<ConstraintViolation<Animal>> violations = validator.validate(animal);
            if (violations.isEmpty()) {
                validatedAnimals.add(animal);
            } else {
                // Handle validation errors if needed
                for (ConstraintViolation<Animal> violation : violations) {
                    System.err.println("Validation error: " + violation.getMessage());
                }
            }
        }

        animalRepository.saveAll(validatedAnimals);
    }
}
