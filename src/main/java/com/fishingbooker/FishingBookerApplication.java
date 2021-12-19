package com.fishingbooker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication(scanBasePackages = {"com.fishingbooker"})
@RestController
public class FishingBookerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FishingBookerApplication.class, args);

    }
}