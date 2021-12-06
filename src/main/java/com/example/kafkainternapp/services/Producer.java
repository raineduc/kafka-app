package com.example.kafkainternapp.services;

import com.example.kafkainternapp.dto.Record;
import com.example.kafkainternapp.entities.ProducedEntity;
import com.example.kafkainternapp.repositories.ProducedEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
    private ProducedEntityRepository producedEntityRepository;
    private KafkaTemplate<String, Record> kafkaProducer;

    @Autowired
    public Producer(ProducedEntityRepository producedEntityRepository, KafkaTemplate<String, Record> kafkaProducer) {
        this.producedEntityRepository = producedEntityRepository;
        this.kafkaProducer = kafkaProducer;
    }

    public void produceMessages(String topicName) {
        for (ProducedEntity entity: producedEntityRepository.findAll()) {
            kafkaProducer.send(topicName, new Record(entity.getId(), entity.getName(), entity.getDateTime()));
        }
    }
}
