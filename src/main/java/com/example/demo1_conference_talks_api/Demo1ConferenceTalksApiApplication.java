package com.example.demo1_conference_talks_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Demo1ConferenceTalksApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo1ConferenceTalksApiApplication.class, args);
    }

}
