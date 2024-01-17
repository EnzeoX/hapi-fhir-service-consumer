package com.job.testlistener.handler.consumer;

import com.job.testlistener.service.HapiFhriService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class KafkaConsumer {

    private final HapiFhriService hapiFhriService;

    private final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    public KafkaConsumer(HapiFhriService hapiFhriService) {
        this.hapiFhriService = hapiFhriService;
    }

    @KafkaListener(
            topics = "${application.kafka.topic}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(String message) {
        Objects.requireNonNull(message);
        log.info("Kafka message collected:\n {}", message);
        hapiFhriService.sendEncounterBundleToHapi(message);
    }
}
