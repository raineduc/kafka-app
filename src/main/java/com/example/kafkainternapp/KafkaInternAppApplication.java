package com.example.kafkainternapp;

import com.example.kafkainternapp.services.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaInternAppApplication implements CommandLineRunner {
    private final String PRODUCER_MODE = "produce";
    private final String CONSUMER_MODE = "consume";
    @Value("${app_mode}")
    private String mode;
    private final Producer producerService;

    @Autowired
    public KafkaInternAppApplication(Producer producerService) {
        this.producerService = producerService;
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaInternAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Kafka app");
        System.out.println(mode);
        if (mode.equals(PRODUCER_MODE)) {
            producerService.produceMessages("App1");
        }
    }
}
