package com.fishingbooker.service.impl;

import com.fishingbooker.model.Boat;
import com.fishingbooker.repository.BoatRepository;
import com.fishingbooker.service.BoatService;
import com.fishingbooker.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BoatServiceImpl implements BoatService{

    @Autowired
    private BoatRepository boatRepository;

    @Override
    public Optional<Boat> findById(Long id) {
        return boatRepository.findById(id);
    }

    @Override
    public List<Boat> findAll() {
        return boatRepository.findAll();
    }

    @Override
    public Boat registerBoat(Boat boat, MultipartFile[] files) throws IOException {
        Boat savedBoat = boatRepository.save(boat);
        String images = "";
        int i = 0;
        for (MultipartFile image : files) {
            i += 1;
            String fileName = "c" + savedBoat.getId() + "." + i + ".jpg";
            images += fileName + ';';
            FileUploadUtil.saveFile(FileUploadUtil.getImageFolder("cottages"), fileName, image);
        }
        savedBoat.setImages(images.substring(0, images.length() - 1));
        return boatRepository.save(savedBoat);
    }
}
