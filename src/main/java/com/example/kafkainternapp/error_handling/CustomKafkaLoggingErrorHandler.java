package com.example.kafkainternapp.error_handling;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.ListenerUtils;
import org.springframework.kafka.listener.MessageListenerContainer;

import java.util.Iterator;

// like CommonLoggingErrorHandler, but using Logback
public class CustomKafkaLoggingErrorHandler implements CommonErrorHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomKafkaLoggingErrorHandler.class);
    private boolean ackAfterHandle = true;

    public CustomKafkaLoggingErrorHandler() {
    }

    public boolean isAckAfterHandle() {
        return this.ackAfterHandle;
    }

    public void setAckAfterHandle(boolean ackAfterHandle) {
        this.ackAfterHandle = ackAfterHandle;
    }

    public void handleRecord(Exception thrownException, ConsumerRecord<?, ?> record, Consumer<?, ?> consumer, MessageListenerContainer container) {
        LOGGER.error("Error occured while processing: " + ListenerUtils.recordToString(record), thrownException);
    }

    public void handleBatch(Exception thrownException, ConsumerRecords<?, ?> data, Consumer<?, ?> consumer, MessageListenerContainer container, Runnable invokeListener) {
        StringBuilder message = new StringBuilder("Error occurred while processing:\n");
        Iterator var7 = data.iterator();

        while(var7.hasNext()) {
            ConsumerRecord<?, ?> record = (ConsumerRecord)var7.next();
            message.append(ListenerUtils.recordToString(record)).append('\n');
        }

        LOGGER.error(message.substring(0, message.length() - 1), thrownException);
    }

    public void handleOtherException(Exception thrownException, Consumer<?, ?> consumer, MessageListenerContainer container, boolean batchListener) {
        LOGGER.error("Error occurred while not processing records", thrownException);
    }
}
