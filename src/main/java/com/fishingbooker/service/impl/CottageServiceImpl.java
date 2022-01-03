package com.fishingbooker.service.impl;

import com.fishingbooker.model.Cottage;
import com.fishingbooker.repository.CottageRepository;
import com.fishingbooker.service.CottageService;
import com.fishingbooker.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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
            FileUploadUtil.saveFile(FileUploadUtil.getImageFolder("cottages"), fileName, image);
        }
        savedCottage.setImages(images.substring(0, images.length() - 1));
        return cottageRepository.save(savedCottage);
    }

}
