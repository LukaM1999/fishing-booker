package com.fishingbooker.service.impl;

import com.fishingbooker.model.Cottage;
import com.fishingbooker.repository.CottageRepository;
import com.fishingbooker.service.CottageService;
import com.fishingbooker.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CottageServiceImpl implements CottageService{

    @Autowired
    private CottageRepository cottageRepository;

    @Override
    public Optional<Cottage> findById(Long id) {
        return cottageRepository.findById(id);
    }

    @Override
    public List<Cottage> findAll() {
        return cottageRepository.findAll();
    }

    @Override
    public Cottage registerCottage(Cottage cottage, MultipartFile[] files) throws IOException {
        Cottage savedCottage = cottageRepository.save(cottage);
        String images = "";
        int i = 0;
        for (MultipartFile image : files) {
            i += 1;
            String fileName = "c" + savedCottage.getId() + "." + i + ".jpg";
            images += fileName + ';';
            FileUploadUtil.saveFile(FileUploadUtil.getImageFolder(""), fileName, image);
        }
        savedCottage.setImages(images.substring(0, images.length() - 1));
        return cottageRepository.save(savedCottage);
    }

    @Override
    public List<Cottage> findAllByOwnerUsername(String username) {
        return cottageRepository.findAllByOwnerUsername(username);
    }

    @Override
    public boolean deleteCottage(Long cottageId) {
        Optional<Cottage> optional = cottageRepository.findById(cottageId);
        Cottage cottage = optional.orElse(null);
        if(cottage == null) return false;
        cottageRepository.deleteById(cottageId);
        return true;
    }

}
