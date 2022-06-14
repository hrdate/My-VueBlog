package com.vueblog.controller;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic newTopic(){
        String topic = "vueBlogTopic";
        Integer partition = 3;
        short replication = 2;
        return new NewTopic(topic, partition, replication);
    }
}
