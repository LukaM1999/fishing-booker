package com.fishingbooker.service.impl;

import com.fishingbooker.model.Cottage;
import com.fishingbooker.model.Reservation;
import com.fishingbooker.repository.CottageRepository;
import com.fishingbooker.repository.ReservationRepository;
import com.fishingbooker.service.CottageService;
import com.fishingbooker.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CottageServiceImpl implements CottageService{

    @Autowired
    private CottageRepository cottageRepository;
    @Autowired
    private ReservationRepository reservationRepository;

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
        String images = savedCottage.getImages();
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
                String fileName = "c" + savedCottage.getId() + "." + i + ".jpg";
                images += fileName + ';';
                FileUploadUtil.saveFile(FileUploadUtil.getImageFolder(""), fileName, image);
            }
            savedCottage.setImages(images);
        }
        return cottageRepository.save(savedCottage);
    }

    @Override
    public List<Cottage> findAllByOwnerUsername(String username) {
        return cottageRepository.findAllByOwnerUsername(username);
    }

    @Override
    @Transactional
    public boolean deleteCottage(Long cottageId){
        Cottage cottage = null;
        try {
            cottage = cottageRepository.findByIdLocked(cottageId);
            System.out.println(cottage.getName());
            //Thread.sleep(3000);

            if(cottage == null) return false;
            List<Reservation> reservations = reservationRepository.findAll();
            for(Reservation reservation : reservations){
                if(reservation.getName().equals(cottage.getName()) && reservation.getEndTime().isAfter(LocalDateTime.now())){
                    return false;
                }
            }
            cottageRepository.deleteById(cottageId);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
