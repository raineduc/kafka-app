package com.example.kafkainternapp.services;

import com.example.kafkainternapp.entities.ProducedEntity;
import com.example.kafkainternapp.repositories.ProducedEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
    private ProducedEntityRepository producedEntityRepository;
    private KafkaTemplate<String, ProducedEntity> kafkaProducer;

    @Autowired
    public Producer(ProducedEntityRepository producedEntityRepository, KafkaTemplate<String, ProducedEntity> kafkaProducer) {
        this.producedEntityRepository = producedEntityRepository;
        this.kafkaProducer = kafkaProducer;
    }

    public void produceMessages(String topicName) {
        for (ProducedEntity entity: producedEntityRepository.findAll()) {
            System.out.println("a");
            kafkaProducer.send(topicName, entity);
        }
    }
}