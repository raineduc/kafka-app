package com.example.kafkainternapp.services;

import com.example.kafkainternapp.dto.Record;
import com.example.kafkainternapp.entities.ProducedEntity;
import com.example.kafkainternapp.repositories.ProducedEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
    private ProducedEntityRepository producedEntityRepository;
    private KafkaTemplate<String, Record> kafkaProducer;
    Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    public Producer(ProducedEntityRepository producedEntityRepository, KafkaTemplate<String, Record> kafkaProducer) {
        this.producedEntityRepository = producedEntityRepository;
        this.kafkaProducer = kafkaProducer;
    }

    public void produceMessages(String topicName) {
        logger.info("Sending DB records...");
        for (ProducedEntity entity: producedEntityRepository.findAll()) {
            kafkaProducer.send(topicName, new Record(entity.getId(), entity.getName(), entity.getDateTime()));
            logger.trace(String.format("Entity with id = %d, name = %s, timestamp = %3$TD %3$TT has been sent",
                    entity.getId(), entity.getName(), entity.getDateTime()));
        }
        logger.info("All records have been sent");
    }
}
