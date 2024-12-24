package com.example.sparta_modo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpartaModoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpartaModoApplication.class, args);
    }

}
