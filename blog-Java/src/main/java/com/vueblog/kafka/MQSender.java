package com.vueblog.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class MQSender {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    public void sendMessage(Object message) {
        kafkaTemplate.send("vueBlogTopic", message);
    }
    public void sendMessage(String key, Object message) {
        kafkaTemplate.send("vueBlogTopic", key, message);
    }

    public void sendMessage(String topic, String key, Object message) {
        kafkaTemplate.send(topic, key, message);
    }

    public void sendMessage(String topic, Integer partition ,String key, Object message) {
        kafkaTemplate.send(topic, partition, key, message);
    }

}
