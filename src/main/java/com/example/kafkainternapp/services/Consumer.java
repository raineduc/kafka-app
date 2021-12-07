package com.example.kafkainternapp.services;

import com.example.kafkainternapp.KafkaInternAppApplication;
import com.example.kafkainternapp.dto.Record;
import com.example.kafkainternapp.entities.ConsumedEntity;
import com.example.kafkainternapp.repositories.ConsumedEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.event.ConsumerStartedEvent;
import org.springframework.kafka.event.ListenerContainerIdleEvent;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Consumer {
    @Value("${app_mode}")
    private String appMode;
    private final KafkaListenerEndpointRegistry registry;
    private final ConsumedEntityRepository consumedEntityRepository;
    Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    public Consumer(ConsumedEntityRepository consumedEntityRepository, KafkaListenerEndpointRegistry registry) {
        this.consumedEntityRepository = consumedEntityRepository;
        this.registry = registry;
    }

    @KafkaListener(topics = "${kafka_topic}", groupId = "${kafka_consumer_group-id}")
    public void listenToMessages(@Payload List<Record> messages) {
        logger.trace("A batch of messages arrived");
        for (Record record: messages) {
            logger.trace(String.format("Entity with id = %d, name = %s, timestamp = %3$TD %3$TT has arrived",
                    record.getId(), record.getName(), record.getDateTime()));
        }
        List<ConsumedEntity> entities = messages.stream()
                .map(message -> new ConsumedEntity(message.getId(), message.getName(), message.getDateTime()))
                .collect(Collectors.toList());
        consumedEntityRepository.saveAll(entities);
        logger.trace("The batch saved");
    }

    @EventListener
    public void eventHandler(ListenerContainerIdleEvent event) {
        logger.info("Stop listening topic");
        registry.stop();
    }

    @EventListener
    public void eventHandler(ConsumerStartedEvent event) {
        logger.info("Start listening topic");
    }
}
