package com.example.kafkainternapp.config;

import com.example.kafkainternapp.dto.Record;
import com.example.kafkainternapp.error_handling.CustomKafkaLoggingErrorHandler;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.CommonLoggingErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value("${kafka_listener_idle-event-interval}")
    private String idleEventInterval;
    @Value("${app_mode}")
    private String appMode;
    private final Environment env;

    public KafkaConsumerConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public ConsumerFactory<String, Record> recordConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("kafka_consumer_bootstrap-servers"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, env.getProperty("kafka_consumer_group-id"));
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);
        props.put(
                JsonDeserializer.TRUSTED_PACKAGES,
                "*"
        );
        props.put(
                ConsumerConfig.MAX_POLL_RECORDS_CONFIG,
                "50"
        );
        props.put(
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                env.getProperty("spring.kafka.consumer.auto-offset-reset")
        );

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Record>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, Record> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(recordConsumerFactory());
        factory.setCommonErrorHandler(new CustomKafkaLoggingErrorHandler());
        factory.setBatchListener(true);
        factory.setAutoStartup(appMode.equals("consume"));
        factory.getContainerProperties().setIdleEventInterval(Long.parseLong(idleEventInterval));
        return factory;
    }
}
