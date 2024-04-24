package com.goodvice.pumbtest.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.goodvice.pumbtest.model.Animal;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class AnimalXmlParser implements AnimalFileParser {
    @Override
    public List<Animal> parse(MultipartFile file) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        String xmlContent = new String(file.getBytes());

        return xmlMapper.readValue(xmlContent, new TypeReference<>() {
        });
    }
}
