package com.goodvice.pumbtest.controller;

import com.goodvice.pumbtest.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class AnimalController {
    private final AnimalService animalService;

    @PostMapping("files/uploads")
    public ResponseEntity<?> uploadFromFile(@RequestParam("file") MultipartFile file) {
        return animalService.addFromFile(file);
    }

    @GetMapping("files")
    public ResponseEntity<?> getAnimals(@RequestParam(required = false) String type,
                                        @RequestParam(required = false, defaultValue = "-1") int category,
                                        @RequestParam(required = false) String sex,
                                        @RequestParam(required = false) String sortBy) {
        return animalService.getAllFiltered(type, category, sex, sortBy);
    }

}
