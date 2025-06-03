package com.aytaj.wellbeing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class WellBeingApplication {

    public static void main(String[] args) {
        SpringApplication.run(WellBeingApplication.class, args);
    }

}
