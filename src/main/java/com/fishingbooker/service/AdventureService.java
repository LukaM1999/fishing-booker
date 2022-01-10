package com.fishingbooker.service;

import com.fishingbooker.model.Adventure;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AdventureService {
    Adventure registerAdventure(Adventure readValue, MultipartFile[] files)throws IOException;
}
