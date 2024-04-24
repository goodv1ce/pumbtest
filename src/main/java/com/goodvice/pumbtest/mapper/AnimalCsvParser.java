package com.goodvice.pumbtest.mapper;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.goodvice.pumbtest.model.Animal;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

public class AnimalCsvParser implements AnimalFileParser {
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
