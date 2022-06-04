package com.fishingbooker.service.impl;

import com.fishingbooker.model.Boat;
import com.fishingbooker.model.Cottage;
import com.fishingbooker.repository.BoatRepository;
import com.fishingbooker.service.BoatService;
import com.fishingbooker.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
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
        String images = savedBoat.getImages();
        int i;
        if(images.length() == 0){
            i = 0;
        }
        else {
            String[] strings = images.split(";");
            String lastImg = strings[strings.length - 1];
            String[] niz = lastImg.split("\\.");
            String number = niz[1];
            i = Integer.parseInt(number);
        }
        if (!Objects.equals(files[0].getOriginalFilename(), "blob")) {
            for (MultipartFile image : files) {
                i += 1;
                String fileName = "c" + savedBoat.getId() + "." + i + ".jpg";
                images += fileName + ';';
                FileUploadUtil.saveFile(FileUploadUtil.getImageFolder(""), fileName, image);
            }
            savedBoat.setImages(images);
        }
        return boatRepository.save(savedBoat);
    }

    @Override
    public List<Boat> findAllByOwnerUsername(String username) {
        return boatRepository.findAllByOwnerUsername(username);
    }

    @Override
    public boolean deleteBoat(Long boatId) {
        Optional<Boat> optional = boatRepository.findById(boatId);
        Boat boat = optional.orElse(null);
        if(boat == null) return false;
        boatRepository.deleteById(boatId);
        return true;
    }
}
