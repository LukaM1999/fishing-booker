package com.fishingbooker.service.impl;

import com.fishingbooker.model.Adventure;
import com.fishingbooker.repository.AdventureRepository;
import com.fishingbooker.service.AdventureService;
import com.fishingbooker.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AdventureServiceImpl implements AdventureService {

    @Autowired
    private AdventureRepository adventureRepository;

    @Override
    public Adventure registerAdventure(Adventure adventure, MultipartFile[] files) throws IOException {
        Adventure savedAdventure = adventureRepository.save(adventure);
        String images = "";
        int i = 0;
        for (MultipartFile image : files) {
            i += 1;
            String fileName = "a" + savedAdventure.getId() + "." + i + ".jpg";
            images += fileName + ';';
            FileUploadUtil.saveFile(FileUploadUtil.getImageFolder("adventures"), fileName, image);
        }
        savedAdventure.setImages(images.substring(0, images.length() - 1));
        return adventureRepository.save(savedAdventure);
    }
}
