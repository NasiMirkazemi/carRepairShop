package com.first.carRepairShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CarRepairShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRepairShopApplication.class, args);
    }

}
