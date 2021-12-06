package com.example.kafkainternapp.services;

import com.example.kafkainternapp.dto.Record;
import com.example.kafkainternapp.entities.ConsumedEntity;
import com.example.kafkainternapp.repositories.ConsumedEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.event.ListenerContainerIdleEvent;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(name = "app_mode", havingValue = "consume")
public class Consumer {
    private final KafkaListenerEndpointRegistry registry;
    private final ConsumedEntityRepository consumedEntityRepository;

    @Autowired
    public Consumer(ConsumedEntityRepository consumedEntityRepository, KafkaListenerEndpointRegistry registry) {
        System.out.println("Entered in Consumer");
        this.consumedEntityRepository = consumedEntityRepository;
        this.registry = registry;
    }

    @KafkaListener(topics = "${kafka_topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenToMessages(@Payload List<Record> messages) {
        System.out.println("a");
        for (Record record: messages) {
            System.out.println(record.getId());
        }
        List<ConsumedEntity> entities = messages.stream()
                .map(message -> new ConsumedEntity(message.getId(), message.getName(), message.getDateTime()))
                .collect(Collectors.toList());
        consumedEntityRepository.saveAll(entities);
    }

    @EventListener
    public void eventHandler(ListenerContainerIdleEvent event) {
        System.out.println("Stop listener container");
        registry.stop();
    }
}
