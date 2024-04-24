package com.goodvice.pumbtest.service;

import com.goodvice.pumbtest.mapper.AnimalCsvMapper;
import com.goodvice.pumbtest.mapper.AnimalFileMapper;
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

        AnimalFileMapper fileMapper;

        switch (contentType) {
            case "text/csv":
                fileMapper = new AnimalCsvMapper();
                break;
            case "application/xml":
                fileMapper = new AnimalXmlMapper();
                break;
            default:
                //todo handle unknown format send error unsupported
                return;
        }

        try {
            List<Animal> animalList = fileMapper.map(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
