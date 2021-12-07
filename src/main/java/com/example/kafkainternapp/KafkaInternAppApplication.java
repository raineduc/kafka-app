package com.example.kafkainternapp;

import com.example.kafkainternapp.services.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaInternAppApplication implements CommandLineRunner {
    @Value("${app_mode}")
    private String mode;
    @Value("${kafka_topic}")
    private String topic;
    private final Producer producerService;
    Logger logger = LoggerFactory.getLogger(KafkaInternAppApplication.class);

    @Autowired
    public KafkaInternAppApplication(Producer producerService) {
        this.producerService = producerService;
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaInternAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Start application");
        logger.info(String.format("Chosen mode: %s", mode));
        if (Mode.PRODUCE.toString().equals(mode)) {
            producerService.produceMessages(topic);
        }
    }
}
