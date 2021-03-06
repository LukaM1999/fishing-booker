package com.fishingbooker.service.impl;

import com.fishingbooker.model.Adventure;
import com.fishingbooker.model.Reservation;
import com.fishingbooker.repository.AdventureRepository;
import com.fishingbooker.repository.ReservationRepository;
import com.fishingbooker.service.AdventureService;
import com.fishingbooker.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdventureServiceImpl implements AdventureService {

    @Autowired
    private AdventureRepository adventureRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public Adventure registerAdventure(Adventure adventure, MultipartFile[] files) throws IOException {
        Adventure savedAdventure = adventureRepository.save(adventure);
        String images = savedAdventure.getImages();
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
                String fileName = "a" + savedAdventure.getId() + "." + i + ".jpg";
                images += fileName + ';';
                FileUploadUtil.saveFile(FileUploadUtil.getImageFolder(""), fileName, image);
            }
            savedAdventure.setImages(images);
        }
        return adventureRepository.save(savedAdventure);
    }

    @Override
    public Optional<Adventure> findById(Long userId) {
        return adventureRepository.findById(userId);
    }

    @Override
    public List<Adventure> findAll() {
        return adventureRepository.findAll();
    }

    @Override
    public List<Adventure> findAllByOwnerUsername(String username) {
        return adventureRepository.findAllByOwnerUsername(username);
    }

    @Override
    public boolean deleteCottage(Long adventureId) {
        Optional<Adventure> optional = adventureRepository.findById(adventureId);
        Adventure adventure = optional.orElse(null);
        if(adventure == null) return false;
        List<Reservation> reservations = reservationRepository.findAll();
        for(Reservation reservation : reservations){
            if(reservation.getName().equals(adventure.getName()) && reservation.getEndTime().isAfter(LocalDateTime.now())){
                return false;
            }
        }
        adventureRepository.deleteById(adventureId);
        return true;
    }
}
