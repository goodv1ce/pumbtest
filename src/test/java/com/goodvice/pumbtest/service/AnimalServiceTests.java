package com.goodvice.pumbtest.service;

import com.goodvice.pumbtest.model.Animal;
import com.goodvice.pumbtest.repository.AnimalRepository;
import com.goodvice.pumbtest.validator.AnimalValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTests {
    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private AnimalValidator validator;

    @InjectMocks
    private AnimalService animalService;
    private Animal testAnimal;

    @BeforeEach
    public void setup() {
        testAnimal = new Animal();
        testAnimal.setName("testName");
        testAnimal.setType("testType");
        testAnimal.setSex("testSex");
        testAnimal.setCategory(1);
        testAnimal.setCost(20);
    }

    @Test
    public void addFromFile_FileIsNull_HttpStatus400() {
        ResponseEntity<?> response = animalService.addFromFile(null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verifyNoInteractions(validator);
        verifyNoInteractions(animalRepository);
    }

    @Test
    public void addFromFile_FileHasEmptyContent_HttpStatus400() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);
        ResponseEntity<?> response = animalService.addFromFile(file);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verifyNoInteractions(validator);
        verifyNoInteractions(animalRepository);
    }

    @Test
    public void addFromFile_MediaTypeIsNull_HttpStatus400() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getContentType()).thenReturn(null);
        ResponseEntity<?> response = animalService.addFromFile(file);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verifyNoInteractions(validator);
        verifyNoInteractions(animalRepository);
    }

    @Test
    public void addFromFile_MediaTypeIsUnsupported_HttpStatus415() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getContentType()).thenReturn("application/pdf");
        ResponseEntity<?> response = animalService.addFromFile(file);
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
        verifyNoInteractions(validator);
        verifyNoInteractions(animalRepository);
    }

    @Test
    public void addFromFile_MediaTypeIsCsv_HttpStatus200() {
        String csvContent = """
                 Name,Type,Sex,Weight,Cost
                 Buddy,cat,female,41,78
                """;
        MultipartFile file = new MockMultipartFile("mockFile",
                "mockFile.csv",
                "text/csv",
                csvContent.getBytes());

        ResponseEntity<?> response = animalService.addFromFile(file);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void addFromFile_MediaTypeIsXml_HttpStatus200() {
        String xmlContent = """
                 <animals>
                     <animal>
                        <name>Milo</name>
                        <type>cat</type>
                        <sex>male</sex>
                        <weight>40</weight>
                        <cost>51</cost>
                    </animal>
                </animals>
                """;
        MultipartFile file = new MockMultipartFile("mockFile",
                "mockFile.csv",
                "text/xml",
                xmlContent.getBytes());
        ResponseEntity<?> response = animalService.addFromFile(file);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(List.class, response.getBody());
    }

    @Test
    public void addFromFile_ExceptionWhileParsing_ThrowIOException() {
        MultipartFile file = new MockMultipartFile("mockFile",
                "mockFile.csv",
                "text/xml",
                "mockBytes".getBytes());
        ResponseEntity<?> response = animalService.addFromFile(file);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(IOException.class, response.getBody());
        verifyNoInteractions(animalRepository);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getAllFiltered_WithValidInputs_HttpStatus200() {
        when(animalRepository.findAll((Example<Animal>) any()))
                .thenReturn(Collections.singletonList(testAnimal));

        ResponseEntity<?> response =
                animalService.getAllFiltered("testType", 1, "testSex", null);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        response =
                animalService.getAllFiltered("testType", 1, "testSex", "category");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
