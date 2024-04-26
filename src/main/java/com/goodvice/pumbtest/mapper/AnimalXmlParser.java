package com.goodvice.pumbtest.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.goodvice.pumbtest.model.Animal;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * An implementation of {@link AnimalFileParser} that parses XML files containing animal data.
 * This parser reads the contents of an XML file and maps them to a list of {@link Animal} objects.
 *
 * @author goodvice
 */
public class AnimalXmlParser implements AnimalFileParser {

    /**
     * Parses the provided XML file containing animal data.
     *
     * @param file The XML file to parse.
     * @return A list of {@link Animal} objects parsed from the XML file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    @Override
    public List<Animal> parse(MultipartFile file) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        String xmlContent = new String(file.getBytes());

        return xmlMapper.readValue(xmlContent, new TypeReference<>() {
        });
    }
}
