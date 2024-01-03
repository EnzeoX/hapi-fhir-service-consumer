package com.job.testlistener.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

//@EnableKafka
@Configuration
public class KafkaConfig {

//    @Value(value = "${spring.kafka.bootstrap-servers}")
//    private String kafkaAddress;
//
//    @Value(value = "${spring.kafka.consumer.group-id}")
//    private String kafkaGroupId;
//
//    private static final String KAFKA_KEY_DESERIALIZER_PARAMETER = "org.apache.kafka.common.serialization.StringDeserializer";
//    private static final String KAFKA_VALUE_DESERIALIZER_PARAMETER = "org.apache.kafka.common.serialization.StringDeserializer";
//
//    @Bean
//    public ConsumerFactory<String, String> consumerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KAFKA_KEY_DESERIALIZER_PARAMETER);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KAFKA_VALUE_DESERIALIZER_PARAMETER);
//        return new DefaultKafkaConsumerFactory<>(props);
//    }

//    @Bean
//    public NewTopic kafkaHapiFhirTopic() {
//        return TopicBuilder.name("hapi-fhir-topic")
//                .build();
//    }
}
