package com.goodvice.pumbtest.controller;

import com.goodvice.pumbtest.model.Animal;
import com.goodvice.pumbtest.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AnimalController {
    private final AnimalService animalService;

    @PostMapping(value = "files/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Uploads data from a file")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Upload successful. The newly added data is included in the response.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Animal.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Processing failed due to an invalid file.",
                    content = {
                            @Content(
                                    mediaType = "text/plain",
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject("Error: File is null or empty. Unable to process.")
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "415",
                    description = "Processing failed due to an unsupported media type.",
                    content = {
                            @Content(
                                    mediaType = "text/plain",
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject("Error: Content type is unsupported for processing.")
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Parsing failed.",
                    content = {
                            @Content(
                                    mediaType = "text/plain",
                                    schema = @Schema(implementation = IOException.class)
                            )
                    }
            ),
    })
    public ResponseEntity<?> uploadFromFile(@RequestPart("file") MultipartFile file) {
        return animalService.addFromFile(file);
    }

    @GetMapping("files")
    @Operation(summary = "Retrieves a list of animals filtered and/or sorted. Can be filtered by type/category/sex and " +
            "sorted by any field.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The list of animals was fetched successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Animal.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid parameters provided",
                    content = {
                            @Content(
                                    mediaType = "text/plain",
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject("No property '123' found for type 'Animal'")
                            )
                    }
            )
    })
    public ResponseEntity<?> getAnimals(
            @Parameter(description = "Type of the animal to be filtered by.", example = "cat")
            @RequestParam(required = false)
            String type,

            @Parameter(description = "Category of the animal to be filtered by.", example = "1")
            @RequestParam(required = false, defaultValue = "-1")
            int category,

            @Parameter(description = "Sex of the animal to be filtered by.", example = "female")
            @RequestParam(required = false)
            String sex,

            @Parameter(description = "Field of the animal to be sorted by", example = "cost")
            @RequestParam(required = false)
            String sortBy
    ) {
        return animalService.getAllFiltered(type, category, sex, sortBy);
    }

}
