package com.goodvice.pumbtest.mapper;

import com.goodvice.pumbtest.model.Animal;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AnimalFileMapper {
    public List<Animal> map(MultipartFile file) throws IOException;
}
