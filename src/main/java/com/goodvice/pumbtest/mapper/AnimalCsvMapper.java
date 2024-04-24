package com.goodvice.pumbtest.mapper;

import com.goodvice.pumbtest.model.Animal;
import com.goodvice.pumbtest.model.csv.AnimalCsvBean;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalCsvMapper implements AnimalFileMapper{
    @Override
    public List<Animal> map(MultipartFile file) throws IOException {
        Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

        CsvToBean<AnimalCsvBean> csvToBean = new CsvToBeanBuilder<AnimalCsvBean>(reader)
                .withType(AnimalCsvBean.class)
                .withIgnoreLeadingWhiteSpace(true)
                .withIgnoreEmptyLine(true)
                .build();

        List<Animal> animalList = new ArrayList<>();

        for (AnimalCsvBean bean : csvToBean.parse()) {
            animalList.add(
                    Animal.builder()
                    .name(bean.getName())
                    .type(bean.getType())
                    .sex(bean.getSex())
                    .cost(bean.getCost())
                    .weight(bean.getWeight())
                    .build()
            );
        }
        return animalList;
    }
}
