package com.example.kafkainternapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaInternAppApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(KafkaInternAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Kafka app");
    }
}
