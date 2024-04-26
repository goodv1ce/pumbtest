package com.goodvice.pumbtest.mapper;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.goodvice.pumbtest.model.Animal;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

/**
 * An implementation of {@link AnimalFileParser} that parses CSV files containing animal data.
 * This parser reads the contents of a CSV file and maps them to a list of {@link Animal} objects.
 *
 * @author goodvice
 */
public class AnimalCsvParser implements AnimalFileParser {

    /**
     * Parses the provided CSV file containing animal data.
     *
     * @param file The CSV file to parse.
     * @return A list of {@link Animal} objects parsed from the CSV file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    @Override
    public List<Animal> parse(MultipartFile file) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = CsvSchema
                .emptySchema()
                .withHeader()
                .withColumnSeparator(',');

        MappingIterator<Animal> models = csvMapper
                .readerFor(Animal.class)
                .with(csvSchema)
                .readValues(file.getInputStream());

        return models.readAll();
    }
}
