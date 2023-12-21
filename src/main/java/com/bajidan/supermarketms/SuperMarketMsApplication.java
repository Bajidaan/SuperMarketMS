package com.bajidan.supermarketms;

import com.bajidan.supermarketms.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;

@SpringBootApplication
public class SuperMarketMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperMarketMsApplication.class, args);

    }
}
