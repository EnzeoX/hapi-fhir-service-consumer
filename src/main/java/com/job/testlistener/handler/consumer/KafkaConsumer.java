package com.job.testlistener.handler.consumer;

import com.job.testlistener.service.HapiFhriService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class KafkaConsumer {

    private final HapiFhriService hapiFhriService;

    public KafkaConsumer(HapiFhriService hapiFhriService) {
        this.hapiFhriService = hapiFhriService;
    }

    @KafkaListener(
            topics = "${application.kafka.topic}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(String message) {
        Objects.requireNonNull(message);
        hapiFhriService.sendEncounterBundleToHapi(message);
    }
}
