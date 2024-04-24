package com.goodvice.pumbtest.controller;

import com.goodvice.pumbtest.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class AnimalController {
    private final AnimalService animalService;
    @PostMapping("files/uploads")
    public ResponseEntity<String> uploadFromFile(@RequestParam("file") MultipartFile file) {
        animalService.addFromFile(file);
        return null; //todo
    }

}
