package com.fishingbooker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication(scanBasePackages = {"com.fishingbooker"})
@EnableJpaRepositories
@EnableTransactionManagement
public class FishingBookerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FishingBookerApplication.class, args);

    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}