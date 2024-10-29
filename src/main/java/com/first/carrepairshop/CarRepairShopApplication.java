package com.first.carrepairshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories ( basePackages = " com.first.carrepairshop.repository")
public class CarRepairShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRepairShopApplication.class, args);
    }

}
