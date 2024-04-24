package com.goodvice.pumbtest.service;

import com.goodvice.pumbtest.mapper.AnimalCsvParser;
import com.goodvice.pumbtest.mapper.AnimalFileParser;
import com.goodvice.pumbtest.mapper.AnimalXmlParser;
import com.goodvice.pumbtest.model.Animal;
import com.goodvice.pumbtest.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

        try {
            List<Animal> animalList = fileMapper.parse(file);
            animalRepository.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
