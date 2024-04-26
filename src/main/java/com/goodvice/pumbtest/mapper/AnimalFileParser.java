package com.goodvice.pumbtest.mapper;

import com.goodvice.pumbtest.model.Animal;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


/**
 * Interface for parsing files containing animal data.
 * Implementations of this interface are responsible for parsing specific file formats (CSV, XML)
 * and mapping the file contents to a list of {@link Animal} objects.
 *
 * @author goodvice
 */
public interface AnimalFileParser {

    /**
     * Parses the provided file containing animal data.
     *
     * @param file The file to parse.
     * @return A list of {@link Animal} objects parsed from the file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    List<Animal> parse(MultipartFile file) throws IOException;
}
