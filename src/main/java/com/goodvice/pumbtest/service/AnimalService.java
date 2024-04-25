package com.goodvice.pumbtest.service;

import com.goodvice.pumbtest.mapper.AnimalCsvParser;
import com.goodvice.pumbtest.mapper.AnimalFileParser;
import com.goodvice.pumbtest.mapper.AnimalXmlParser;
import com.goodvice.pumbtest.model.Animal;
import com.goodvice.pumbtest.repository.AnimalRepository;
import com.goodvice.pumbtest.validator.AnimalParseValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final AnimalParseValidator validator;

    public ResponseEntity<?> addFromFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error: File is null or empty. Unable to process.");
        }

        String contentType = file.getContentType();

        if (contentType == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error: Content type is not defined. Unable to process");
        }

        AnimalFileParser fileParser;

        switch (contentType) {
            case "text/csv":
                fileParser = new AnimalCsvParser();
                break;
            case "application/xml":
                fileParser = new AnimalXmlParser();
                break;
            default:
                return ResponseEntity
                        .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body("Error: Content type is unsupported for processing.");
        }

        try {
            List<Animal> animalList = fileParser.parse(file);
            validator.validate(animalList);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(animalRepository.saveAll(animalList));
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e);
        }

    }

    public ResponseEntity<?> getAllFiltered(String type,
                                            Integer category,
                                            String sex,
                                            String sortBy
    ) {
        if (category == -1) {
            category = null;
        }

        Animal animal = new Animal();
        animal.setType(type);
        animal.setCategory(category);
        animal.setSex(sex);

        Example<Animal> example = Example.of(animal);
        List<Animal> queryResult;
        if (Objects.nonNull(sortBy)) {
            queryResult = animalRepository.findAll(example, Sort.by(sortBy));
        } else {
            queryResult = animalRepository.findAll(example);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(queryResult);
    }
}
